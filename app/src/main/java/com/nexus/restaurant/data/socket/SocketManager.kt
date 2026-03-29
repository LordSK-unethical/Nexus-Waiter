package com.nexus.restaurant.data.socket

import com.google.gson.Gson
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import com.nexus.restaurant.domain.model.UserRole
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor() {
    private var socket: Socket? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val gson = Gson()

    private val _socketEvents = MutableSharedFlow<SocketEvent>()
    val socketEvents: Flow<SocketEvent> = _socketEvents.asSharedFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected.asStateFlow()

    private var currentRole: UserRole? = null
    private var currentUserId: String? = null
    private var authToken: String? = null
    private var pendingActions = mutableListOf<PendingAction>()

    data class PendingAction(
        val type: String,
        val data: Any
    )

    fun connect(serverUrl: String, token: String? = null) {
        try {
            authToken = token
            val options = IO.Options().apply {
                reconnection = true
                reconnectionAttempts = 5
                reconnectionDelay = 1000
                reconnectionDelayMax = 5000
                timeout = 10000
                auth = token?.let { mapOf("token" to it) }
            }

            socket = IO.socket(serverUrl, options)
            setupListeners()
            socket?.connect()
        } catch (e: Exception) {
            scope.launch {
                _socketEvents.emit(SocketEvent.Error("Failed to connect: ${e.message}"))
            }
        }
    }

    private fun setupListeners() {
        socket?.apply {
            on(Socket.EVENT_CONNECT) {
                scope.launch {
                    _isConnected.value = true
                    _socketEvents.emit(SocketEvent.Connected())
                    // Re-register user after reconnection
                    currentRole?.let { role ->
                        currentUserId?.let { userId ->
                            registerUser(role, userId)
                        }
                    }
                    // Process pending actions
                    processPendingActions()
                    // Request sync
                    requestSync()
                }
            }

            on(Socket.EVENT_DISCONNECT) {
                scope.launch {
                    _isConnected.value = false
                    _socketEvents.emit(SocketEvent.Disconnected())
                }
            }

            on(Socket.EVENT_CONNECT_ERROR) { args ->
                scope.launch {
                    val error = args.firstOrNull()?.toString() ?: "Connection error"
                    _socketEvents.emit(SocketEvent.Error(error))
                }
            }

            // Custom events
            on("orderCreated") { args ->
                scope.launch {
                    val json = args.firstOrNull() as? JSONObject
                    json?.let { parseOrderEvent(it) }?.let { order ->
                        _socketEvents.emit(SocketEvent.NewOrder(order))
                    }
                }
            }

            on("orderStatusUpdated") { args ->
                scope.launch {
                    val json = args.firstOrNull() as? JSONObject
                    json?.let { parseOrderEvent(it) }?.let { order ->
                        _socketEvents.emit(SocketEvent.OrderStatusUpdated(order))
                    }
                }
            }

            on("tableStatusChanged") { args ->
                scope.launch {
                    val json = args.firstOrNull() as? JSONObject
                    json?.let { parseTableEvent(it) }?.let { table ->
                        _socketEvents.emit(SocketEvent.TableUpdated(table))
                    }
                }
            }

            on("syncResponse") { args ->
                scope.launch {
                    try {
                        val data = args.firstOrNull() as? JSONObject
                        val tablesJson = data?.getJSONArray("tables")
                        val ordersJson = data?.getJSONArray("orders")

                        val tables = mutableListOf<Table>()
                        val orders = mutableListOf<Order>()

                        tablesJson?.let { arr ->
                            for (i in 0 until arr.length()) {
                                parseTableEvent(arr.getJSONObject(i))?.let { tables.add(it) }
                            }
                        }

                        ordersJson?.let { arr ->
                            for (i in 0 until arr.length()) {
                                parseOrderEvent(arr.getJSONObject(i))?.let { orders.add(it) }
                            }
                        }

                        _socketEvents.emit(SocketEvent.SyncData(tables, orders))
                    } catch (e: Exception) {
                        _socketEvents.emit(SocketEvent.Error("Sync failed: ${e.message}"))
                    }
                }
            }

            on("notification") { args ->
                scope.launch {
                    try {
                        val data = args.firstOrNull() as? JSONObject
                        val message = data?.getString("message") ?: ""
                        val type = data?.getString("type") ?: "INFO"
                        _socketEvents.emit(SocketEvent.Notification(message,
                            com.nexus.restaurant.domain.model.NotificationType.valueOf(type)))
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            }
        }
    }

    private fun parseOrderEvent(json: JSONObject): Order? {
        return try {
            val itemsArray = json.getJSONArray("items")
            val items = mutableListOf<OrderItem>()

            for (i in 0 until itemsArray.length()) {
                val itemJson = itemsArray.getJSONObject(i)
                items.add(
                    OrderItem(
                        itemId = itemJson.getString("itemId"),
                        name = itemJson.getString("name"),
                        price = itemJson.getDouble("price"),
                        quantity = itemJson.getInt("quantity"),
                        notes = itemJson.optString("notes", "")
                    )
                )
            }

            Order(
                orderId = json.getString("orderId"),
                tableNo = json.getString("tableNo"),
                people = json.getInt("people"),
                items = items,
                status = OrderStatus.valueOf(json.getString("status")),
                notes = json.optString("notes", ""),
                timestamp = json.getLong("timestamp"),
                totalAmount = json.optDouble("totalAmount", 0.0)
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun parseTableEvent(json: JSONObject): Table? {
        return try {
            Table(
                tableNo = json.getString("tableNo"),
                status = TableStatus.valueOf(json.getString("status")),
                capacity = json.getInt("capacity"),
                currentOrder = null,
                reservationName = json.optString("reservationName", null),
                reservationTime = json.optLong("reservationTime", 0).takeIf { it > 0 },
                occupiedSince = json.optLong("occupiedSince", 0).takeIf { it > 0 }
            )
        } catch (e: Exception) {
            null
        }
    }

    fun registerUser(role: UserRole, userId: String) {
        currentRole = role
        currentUserId = userId

        if (_isConnected.value) {
            socket?.emit("register", JSONObject().apply {
                put("role", role.name)
                put("userId", userId)
            })
        }
    }

    fun emitNewOrder(order: Order) {
        val orderJson = JSONObject().apply {
            put("orderId", order.orderId)
            put("tableNo", order.tableNo)
            put("people", order.people)
            put("items", JSONArray().apply {
                order.items.forEach { item ->
                    put(JSONObject().apply {
                        put("itemId", item.itemId)
                        put("name", item.name)
                        put("price", item.price)
                        put("quantity", item.quantity)
                        put("notes", item.notes)
                    })
                }
            })
            put("status", order.status.name)
            put("notes", order.notes)
            put("timestamp", order.timestamp)
            put("totalAmount", order.totalAmount)
        }

        if (_isConnected.value) {
            socket?.emit("newOrder", orderJson)
        } else {
            pendingActions.add(PendingAction("newOrder", orderJson))
        }
    }

    fun emitOrderStatusUpdate(orderId: String, status: String) {
        val data = JSONObject().apply {
            put("orderId", orderId)
            put("status", status)
        }

        if (_isConnected.value) {
            socket?.emit("updateOrderStatus", data)
        } else {
            pendingActions.add(PendingAction("updateOrderStatus", data))
        }
    }

    fun emitCancelOrder(orderId: String) {
        if (_isConnected.value) {
            socket?.emit("cancelOrder", JSONObject().apply {
                put("orderId", orderId)
            })
        } else {
            pendingActions.add(PendingAction("cancelOrder", orderId))
        }
    }

    fun emitTableUpdate(table: Table) {
        val tableJson = JSONObject().apply {
            put("tableNo", table.tableNo)
            put("status", table.status.name)
            put("capacity", table.capacity)
            table.reservationName?.let { put("reservationName", it) }
            table.reservationTime?.let { put("reservationTime", it) }
            table.occupiedSince?.let { put("occupiedSince", it) }
        }

        if (_isConnected.value) {
            socket?.emit("tableUpdate", tableJson)
        } else {
            pendingActions.add(PendingAction("tableUpdate", tableJson))
        }
    }

    fun requestSync() {
        if (_isConnected.value) {
            socket?.emit("syncData", JSONObject())
        }
    }

    private fun processPendingActions() {
        val actions = pendingActions.toList()
        pendingActions.clear()

        actions.forEach { action ->
            when (action.type) {
                "newOrder" -> socket?.emit("newOrder", action.data)
                "updateOrderStatus" -> socket?.emit("updateOrderStatus", action.data)
                "cancelOrder" -> socket?.emit("cancelOrder", action.data)
                "tableUpdate" -> socket?.emit("tableUpdate", action.data)
            }
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
        _isConnected.value = false
    }

    fun isConnected(): Boolean = _isConnected.value
}
