package com.nexus.restaurant.domain.model

data class Order(
    val orderId: String,
    val tableNo: String,
    val people: Int,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val notes: String,
    val timestamp: Long,
    val totalAmount: Double = 0.0
)

data class OrderItem(
    val itemId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val notes: String = ""
)

enum class OrderStatus {
    PENDING,
    PREPARING,
    READY,
    SERVED,
    CANCELLED;

    fun displayName(): String = when (this) {
        PENDING -> "Pending"
        PREPARING -> "Preparing"
        READY -> "Ready"
        SERVED -> "Served"
        CANCELLED -> "Cancelled"
    }
}
