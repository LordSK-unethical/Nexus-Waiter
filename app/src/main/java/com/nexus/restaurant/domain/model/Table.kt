package com.nexus.restaurant.domain.model

data class Table(
    val tableNo: String,
    val status: TableStatus,
    val capacity: Int,
    val currentOrder: Order? = null,
    val reservationName: String? = null,
    val reservationTime: Long? = null,
    val occupiedSince: Long? = null
)

enum class TableStatus {
    AVAILABLE,
    OCCUPIED,
    RESERVED;

    fun displayName(): String = when (this) {
        AVAILABLE -> "Available"
        OCCUPIED -> "Occupied"
        RESERVED -> "Reserved"
    }
}
