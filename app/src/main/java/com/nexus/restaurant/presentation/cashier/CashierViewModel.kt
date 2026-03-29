package com.nexus.restaurant.presentation.cashier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.NetworkConfig
import com.nexus.restaurant.domain.model.Order
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

sealed class CashierEvent {
    data class ShowError(val message: String) : CashierEvent()
    object Logout : CashierEvent()
}

@HiltViewModel
class CashierViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _events = MutableSharedFlow<CashierEvent>()
    val events: SharedFlow<CashierEvent> = _events.asSharedFlow()

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
        socketRepository.registerUser(com.nexus.restaurant.domain.model.UserRole.CASHIER, "cashier_1")
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
            is SocketEvent.SyncData -> {
                if (event.orders.isNotEmpty()) {
                    _orders.value = event.orders
                }
            }
            is SocketEvent.Error -> {
                viewModelScope.launch {
                    _events.emit(CashierEvent.ShowError(event.message))
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

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                socketRepository.requestSync()
            } catch (e: Exception) {
                _events.emit(CashierEvent.ShowError("Refresh failed: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        socketRepository.disconnect()
        viewModelScope.launch {
            _events.emit(CashierEvent.Logout)
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}
