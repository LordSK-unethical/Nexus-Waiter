package com.nexus.restaurant.presentation.waiter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.NetworkConfig
import com.nexus.restaurant.domain.model.MenuItem
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import com.nexus.restaurant.domain.repository.MenuRepository
import com.nexus.restaurant.domain.repository.OrderRepository
import com.nexus.restaurant.domain.repository.SocketRepository
import com.nexus.restaurant.domain.repository.TableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class WaiterEvent {
    data class ShowError(val message: String) : WaiterEvent()
    data class ShowSuccess(val message: String) : WaiterEvent()
    object OrderPlaced : WaiterEvent()
    object Logout : WaiterEvent()
}

@HiltViewModel
class WaiterViewModel @Inject constructor(
    private val tableRepository: TableRepository,
    private val orderRepository: OrderRepository,
    private val socketRepository: SocketRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _tables = MutableStateFlow<List<Table>>(emptyList())
    val tables: StateFlow<List<Table>> = _tables.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    // Cart: Map<itemId, quantity> - single source of truth
    private val _cartItems = MutableStateFlow<Map<String, Int>>(emptyMap())
    val cartItems: StateFlow<Map<String, Int>> = _cartItems.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _events = MutableSharedFlow<WaiterEvent>()
    val events: SharedFlow<WaiterEvent> = _events.asSharedFlow()

    init {
        loadInitialData()
        observeSocketEvents()
        connectToServer()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Load menu items from Room DB (or create sample if empty)
                menuRepository.refreshMenuItems()
                menuRepository.getMenuItems().collect { items ->
                    _menuItems.value = items
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _events.emit(WaiterEvent.ShowError("Failed to load menu: ${e.message}"))
            }
        }

        // Observe tables from repository
        viewModelScope.launch {
            tableRepository.getTables().collect { tableList ->
                if (tableList.isNotEmpty()) {
                    _tables.value = tableList
                } else {
                    // Use sample tables if DB is empty
                    _tables.value = createSampleTables()
                }
            }
        }

        // Observe orders from repository
        viewModelScope.launch {
            orderRepository.getOrders().collect { orderList ->
                _orders.value = orderList
            }
        }
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            socketRepository.isConnected.collect { connected ->
                _isConnected.value = connected
            }
        }

        viewModelScope.launch {
            socketRepository.socketEvents.collect { event ->
                handleSocketEvent(event)
            }
        }
    }

    private fun connectToServer() {
        socketRepository.connect(NetworkConfig.BASE_URL)
        socketRepository.registerUser(com.nexus.restaurant.domain.model.UserRole.WAITER, "waiter_1")
    }

    private fun handleSocketEvent(event: SocketEvent) {
        when (event) {
            is SocketEvent.NewOrder -> {
                val currentOrders = _orders.value.toMutableList()
                currentOrders.add(0, event.order)
                _orders.value = currentOrders
            }
            is SocketEvent.OrderStatusUpdated -> {
                updateOrderInList(event.order)
            }
            is SocketEvent.TableUpdated -> {
                updateTableInList(event.table)
            }
            is SocketEvent.SyncData -> {
                if (event.tables.isNotEmpty()) _tables.value = event.tables
                if (event.orders.isNotEmpty()) _orders.value = event.orders
            }
            is SocketEvent.Error -> {
                viewModelScope.launch {
                    _events.emit(WaiterEvent.ShowError(event.message))
                }
            }
            else -> {}
        }
    }

    private fun updateOrderInList(updatedOrder: Order) {
        val currentOrders = _orders.value.toMutableList()
        val index = currentOrders.indexOfFirst { it.orderId == updatedOrder.orderId }
        if (index != -1) {
            currentOrders[index] = updatedOrder
            _orders.value = currentOrders
        }
    }

    private fun updateTableInList(updatedTable: Table) {
        val currentTables = _tables.value.toMutableList()
        val index = currentTables.indexOfFirst { it.tableNo == updatedTable.tableNo }
        if (index != -1) {
            currentTables[index] = updatedTable
            _tables.value = currentTables
        }
    }

    // ========== CART OPERATIONS - FIXED ==========
    
    fun getCartItemQuantity(itemId: String): Int {
        return _cartItems.value[itemId] ?: 0
    }

    fun getCartItemsWithDetails(): Map<MenuItem, Int> {
        val cart = _cartItems.value
        return _menuItems.value
            .filter { cart.containsKey(it.id) }
            .associateWith { cart[it.id] ?: 0 }
    }

    fun getCartTotal(): Double {
        return getCartItemsWithDetails().entries.sumOf { (item, qty) -> item.price * qty }
    }

    fun getCartItemCount(): Int {
        return _cartItems.value.values.sum()
    }

    fun updateCartQuantity(itemId: String, newQuantity: Int) {
        val currentCart = _cartItems.value.toMutableMap()
        if (newQuantity <= 0) {
            currentCart.remove(itemId)
        } else {
            currentCart[itemId] = newQuantity
        }
        _cartItems.value = currentCart
    }

    fun addToCart(item: MenuItem, quantity: Int = 1) {
        val currentCart = _cartItems.value.toMutableMap()
        currentCart[item.id] = (currentCart[item.id] ?: 0) + quantity
        _cartItems.value = currentCart
        
        viewModelScope.launch {
            _events.emit(WaiterEvent.ShowSuccess("Added ${item.name} x$quantity"))
        }
    }

    fun removeFromCart(itemId: String) {
        val currentCart = _cartItems.value.toMutableMap()
        currentCart.remove(itemId)
        _cartItems.value = currentCart
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }

    // ========== ORDER OPERATIONS ==========

    fun createOrder(table: Table, notes: String = "") {
        val cart = _cartItems.value
        if (cart.isEmpty()) return

        val menuItemsMap = _menuItems.value.associateBy { it.id }
        
        val orderItems = cart.mapNotNull { (itemId, qty) ->
            menuItemsMap[itemId]?.let { menuItem ->
                OrderItem(
                    itemId = menuItem.id,
                    name = menuItem.name,
                    price = menuItem.price,
                    quantity = qty
                )
            }
        }

        if (orderItems.isEmpty()) return

        val totalAmount = orderItems.sumOf { it.price * it.quantity }

        val order = Order(
            orderId = UUID.randomUUID().toString(),
            tableNo = table.tableNo,
            people = table.capacity,
            items = orderItems,
            status = OrderStatus.PENDING,
            notes = notes,
            timestamp = System.currentTimeMillis(),
            totalAmount = totalAmount
        )

        viewModelScope.launch {
            // Create order with error handling
            val result = orderRepository.createOrder(order)
            result.fold(
                onSuccess = {
                    // Update table status
                    tableRepository.updateTableStatus(table.tableNo, TableStatus.OCCUPIED)
                    
                    // Update local state
                    val currentOrders = _orders.value.toMutableList()
                    currentOrders.add(0, order)
                    _orders.value = currentOrders
                    
                    // Clear cart
                    _cartItems.value = emptyMap()
                    
                    _events.emit(WaiterEvent.OrderPlaced)
                    _events.emit(WaiterEvent.ShowSuccess("Order placed for Table ${table.tableNo}"))
                },
                onFailure = { error ->
                    _events.emit(WaiterEvent.ShowError("Failed to place order: ${error.message}"))
                }
            )
        }
    }

    fun logout() {
        socketRepository.disconnect()
        viewModelScope.launch {
            _events.emit(WaiterEvent.Logout)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                socketRepository.requestSync()
                menuRepository.refreshMenuItems()
            } catch (e: Exception) {
                _events.emit(WaiterEvent.ShowError("Refresh failed: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun createSampleTables(): List<Table> {
        return listOf(
            Table("1", TableStatus.AVAILABLE, 4),
            Table("2", TableStatus.OCCUPIED, 4, occupiedSince = System.currentTimeMillis() - 30 * 60 * 1000),
            Table("3", TableStatus.AVAILABLE, 6),
            Table("4", TableStatus.RESERVED, 4, reservationName = "John", reservationTime = System.currentTimeMillis() + 60 * 60 * 1000),
            Table("5", TableStatus.AVAILABLE, 2),
            Table("6", TableStatus.OCCUPIED, 8, occupiedSince = System.currentTimeMillis() - 45 * 60 * 1000),
            Table("7", TableStatus.AVAILABLE, 4),
            Table("8", TableStatus.AVAILABLE, 6)
        )
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}
