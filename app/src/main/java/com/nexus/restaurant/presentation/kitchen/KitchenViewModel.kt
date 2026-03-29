package com.nexus.restaurant.presentation.kitchen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.NetworkConfig
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.repository.OrderRepository
import com.nexus.restaurant.domain.repository.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class KitchenEvent {
    data class ShowError(val message: String) : KitchenEvent()
    data class ShowSuccess(val message: String) : KitchenEvent()
    object NewOrderArrived : KitchenEvent()
    object Logout : KitchenEvent()
}

@HiltViewModel
class KitchenViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _events = MutableSharedFlow<KitchenEvent>()
    val events: SharedFlow<KitchenEvent> = _events.asSharedFlow()

    init {
        loadOrders()
        observeSocketEvents()
        connectToServer()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            orderRepository.getOrders().collect { orderList ->
                _orders.value = orderList
                _isLoading.value = false
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
        socketRepository.registerUser(com.nexus.restaurant.domain.model.UserRole.KITCHEN, "kitchen_1")
    }

    private fun handleSocketEvent(event: SocketEvent) {
        when (event) {
            is SocketEvent.NewOrder -> {
                val currentOrders = _orders.value.toMutableList()
                currentOrders.add(0, event.order)
                _orders.value = currentOrders
                
                viewModelScope.launch {
                    _events.emit(KitchenEvent.NewOrderArrived)
                }
            }
            is SocketEvent.OrderStatusUpdated -> {
                updateOrderInList(event.order)
            }
            is SocketEvent.SyncData -> {
                if (event.orders.isNotEmpty()) {
                    _orders.value = event.orders
                }
            }
            is SocketEvent.Error -> {
                viewModelScope.launch {
                    _events.emit(KitchenEvent.ShowError(event.message))
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

    fun updateOrderStatus(order: Order, newStatus: OrderStatus) {
        viewModelScope.launch {
            val result = orderRepository.updateOrderStatus(order.orderId, newStatus)
            result.fold(
                onSuccess = { updatedOrder ->
                    val currentOrders = _orders.value.toMutableList()
                    val index = currentOrders.indexOfFirst { it.orderId == order.orderId }
                    if (index != -1) {
                        currentOrders[index] = updatedOrder
                        _orders.value = currentOrders
                    }
                    _events.emit(KitchenEvent.ShowSuccess("Order updated to ${newStatus.displayName()}"))
                },
                onFailure = { error ->
                    _events.emit(KitchenEvent.ShowError("Failed to update: ${error.message}"))
                }
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                socketRepository.requestSync()
            } catch (e: Exception) {
                _events.emit(KitchenEvent.ShowError("Refresh failed: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        socketRepository.disconnect()
        viewModelScope.launch {
            _events.emit(KitchenEvent.Logout)
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}
