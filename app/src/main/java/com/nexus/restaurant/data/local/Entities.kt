package com.nexus.restaurant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val tableNo: String,
    val people: Int,
    val items: String, // JSON string
    val status: String,
    val notes: String,
    val timestamp: Long,
    val totalAmount: Double
)

@Entity(tableName = "tables")
data class TableEntity(
    @PrimaryKey val tableNo: String,
    val status: String,
    val capacity: Int,
    val currentOrderId: String?,
    val reservationName: String?,
    val reservationTime: Long?,
    val occupiedSince: Long?
)

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String?,
    val isAvailable: Boolean
)
