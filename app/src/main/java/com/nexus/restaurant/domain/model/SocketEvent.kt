package com.nexus.restaurant.domain.model

sealed class SocketEvent {
    data class Connected(val message: String = "Connected") : SocketEvent()
    data class Disconnected(val message: String = "Disconnected") : SocketEvent()
    data class Error(val message: String) : SocketEvent()
    data class NewOrder(val order: Order) : SocketEvent()
    data class OrderStatusUpdated(val order: Order) : SocketEvent()
    data class TableUpdated(val table: Table) : SocketEvent()
    data class Notification(val message: String, val type: NotificationType = NotificationType.INFO) : SocketEvent()
    data class SyncData(val tables: List<Table>, val orders: List<Order>) : SocketEvent()
}

enum class NotificationType {
    INFO,
    SUCCESS,
    WARNING,
    ERROR
}
