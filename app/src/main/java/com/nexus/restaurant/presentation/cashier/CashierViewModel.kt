package com.nexus.restaurant.presentation.cashier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.repository.OrderRepository
import com.nexus.restaurant.domain.repository.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashierViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _orders = MutableStateFlow(createSampleOrders())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

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

        socketRepository.connect("http://192.168.1.8:3000")
        socketRepository.registerUser(com.nexus.restaurant.domain.model.UserRole.CASHIER, "cashier_1")
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

    private fun createSampleOrders(): List<Order> {
        return listOf(
            Order(
                orderId = "ORD-001",
                tableNo = "2",
                people = 4,
                items = listOf(
                    OrderItem("1", "Burger", 12.99, 2),
                    OrderItem("7", "Cola", 2.99, 2),
                    OrderItem("9", "Cake", 7.99, 1)
                ),
                status = OrderStatus.SERVED,
                notes = "",
                timestamp = System.currentTimeMillis() - 45 * 60 * 1000,
                totalAmount = 43.95
            ),
            Order(
                orderId = "ORD-002",
                tableNo = "5",
                people = 2,
                items = listOf(
                    OrderItem("2", "Pizza", 14.99, 1),
                    OrderItem("8", "Coffee", 3.99, 2)
                ),
                status = OrderStatus.READY,
                notes = "",
                timestamp = System.currentTimeMillis() - 15 * 60 * 1000,
                totalAmount = 22.97
            ),
            Order(
                orderId = "ORD-003",
                tableNo = "8",
                people = 6,
                items = listOf(
                    OrderItem("3", "Pasta", 11.99, 3),
                    OrderItem("5", "Soup", 6.99, 2),
                    OrderItem("10", "Ice Cream", 5.99, 3)
                ),
                status = OrderStatus.PREPARING,
                notes = "",
                timestamp = System.currentTimeMillis() - 20 * 60 * 1000,
                totalAmount = 68.91
            ),
            Order(
                orderId = "ORD-004",
                tableNo = "1",
                people = 4,
                items = listOf(
                    OrderItem("1", "Burger", 12.99, 4),
                    OrderItem("6", "Fries", 4.99, 4)
                ),
                status = OrderStatus.PENDING,
                notes = "",
                timestamp = System.currentTimeMillis() - 5 * 60 * 1000,
                totalAmount = 71.92
            ),
            Order(
                orderId = "ORD-005",
                tableNo = "3",
                people = 2,
                items = listOf(
                    OrderItem("2", "Pizza", 14.99, 1),
                    OrderItem("9", "Cake", 7.99, 1)
                ),
                status = OrderStatus.SERVED,
                notes = "",
                timestamp = System.currentTimeMillis() - 90 * 60 * 1000,
                totalAmount = 22.98
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}
