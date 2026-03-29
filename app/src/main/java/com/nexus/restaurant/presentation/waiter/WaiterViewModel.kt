package com.nexus.restaurant.presentation.waiter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.domain.model.MenuItem
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import com.nexus.restaurant.domain.repository.OrderRepository
import com.nexus.restaurant.domain.repository.SocketRepository
import com.nexus.restaurant.domain.repository.TableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WaiterViewModel @Inject constructor(
    private val tableRepository: TableRepository,
    private val orderRepository: OrderRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _tables = MutableStateFlow(createSampleTables())
    val tables: StateFlow<List<Table>> = _tables.asStateFlow()

    private val _orders = MutableStateFlow(createSampleOrders())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _cartItems = MutableStateFlow<Map<MenuItem, Int>>(emptyMap())
    val cartItems: StateFlow<Map<MenuItem, Int>> = _cartItems.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    init {
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

        // Connect to socket
        socketRepository.connect("http://192.168.1.8:3000")
        socketRepository.registerUser(com.nexus.restaurant.domain.model.UserRole.WAITER, "waiter_1")
    }

    private fun handleSocketEvent(event: com.nexus.restaurant.domain.model.SocketEvent) {
        when (event) {
            is com.nexus.restaurant.domain.model.SocketEvent.NewOrder -> {
                val currentOrders = _orders.value.toMutableList()
                currentOrders.add(0, event.order)
                _orders.value = currentOrders
            }
            is com.nexus.restaurant.domain.model.SocketEvent.OrderStatusUpdated -> {
                updateOrderInList(event.order)
            }
            is com.nexus.restaurant.domain.model.SocketEvent.TableUpdated -> {
                updateTableInList(event.table)
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

    fun addToCart(item: MenuItem, quantity: Int) {
        val currentCart = _cartItems.value.toMutableMap()
        currentCart[item] = (currentCart[item] ?: 0) + quantity
        _cartItems.value = currentCart
    }

    fun removeFromCart(item: MenuItem) {
        val currentCart = _cartItems.value.toMutableMap()
        currentCart.remove(item)
        _cartItems.value = currentCart
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }

    fun createOrder(table: Table) {
        val cartItems = _cartItems.value
        if (cartItems.isEmpty()) return

        val orderItems = cartItems.map { (item, qty) ->
            OrderItem(
                itemId = item.id,
                name = item.name,
                price = item.price,
                quantity = qty
            )
        }

        val totalAmount = orderItems.sumOf { it.price * it.quantity }

        val order = Order(
            orderId = UUID.randomUUID().toString(),
            tableNo = table.tableNo,
            people = table.capacity,
            items = orderItems,
            status = OrderStatus.PENDING,
            notes = "",
            timestamp = System.currentTimeMillis(),
            totalAmount = totalAmount
        )

        viewModelScope.launch {
            orderRepository.createOrder(order)
        }

        // Update table status
        viewModelScope.launch {
            tableRepository.updateTableStatus(table.tableNo, TableStatus.OCCUPIED)
        }

        // Update local state
        val currentOrders = _orders.value.toMutableList()
        currentOrders.add(0, order)
        _orders.value = currentOrders

        // Clear cart
        _cartItems.value = emptyMap()
    }

    fun getSampleMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("1", "Burger", "Juicy beef burger", 12.99, "Main"),
            MenuItem("2", "Pizza", "Margherita pizza", 14.99, "Main"),
            MenuItem("3", "Pasta", "Creamy Alfredo pasta", 11.99, "Main"),
            MenuItem("4", "Salad", "Fresh garden salad", 8.99, "Starter"),
            MenuItem("5", "Soup", "Tomato soup", 6.99, "Starter"),
            MenuItem("6", "Fries", "Crispy fries", 4.99, "Starter"),
            MenuItem("7", "Cola", "Cold cola", 2.99, "Drinks"),
            MenuItem("8", "Coffee", "Hot coffee", 3.99, "Drinks"),
            MenuItem("9", "Cake", "Chocolate cake", 7.99, "Dessert"),
            MenuItem("10", "Ice Cream", "Vanilla ice cream", 5.99, "Dessert")
        )
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

    private fun createSampleOrders(): List<Order> {
        return listOf(
            Order(
                orderId = "ORD-001",
                tableNo = "2",
                people = 4,
                items = listOf(
                    OrderItem("1", "Burger", 12.99, 2),
                    OrderItem("7", "Cola", 2.99, 2)
                ),
                status = OrderStatus.PREPARING,
                notes = "",
                timestamp = System.currentTimeMillis() - 15 * 60 * 1000,
                totalAmount = 31.96
            ),
            Order(
                orderId = "ORD-002",
                tableNo = "6",
                people = 8,
                items = listOf(
                    OrderItem("2", "Pizza", 14.99, 3),
                    OrderItem("3", "Pasta", 11.99, 2),
                    OrderItem("8", "Coffee", 3.99, 4)
                ),
                status = OrderStatus.READY,
                notes = "",
                timestamp = System.currentTimeMillis() - 25 * 60 * 1000,
                totalAmount = 89.93
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}
