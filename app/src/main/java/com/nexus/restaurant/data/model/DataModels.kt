package com.nexus.restaurant.data.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus

object DataMapper {
    private val gson = Gson()

    fun Order.toEntity(): com.nexus.restaurant.data.local.OrderEntity {
        return com.nexus.restaurant.data.local.OrderEntity(
            orderId = orderId,
            tableNo = tableNo,
            people = people,
            items = gson.toJson(items),
            status = status.name,
            notes = notes,
            timestamp = timestamp,
            totalAmount = totalAmount
        )
    }

    fun com.nexus.restaurant.data.local.OrderEntity.toDomain(): Order {
        val itemType = object : TypeToken<List<OrderItem>>() {}.type
        return Order(
            orderId = orderId,
            tableNo = tableNo,
            people = people,
            items = gson.fromJson(items, itemType) ?: emptyList(),
            status = OrderStatus.valueOf(status),
            notes = notes,
            timestamp = timestamp,
            totalAmount = totalAmount
        )
    }

    fun Table.toEntity(): com.nexus.restaurant.data.local.TableEntity {
        return com.nexus.restaurant.data.local.TableEntity(
            tableNo = tableNo,
            status = status.name,
            capacity = capacity,
            currentOrderId = currentOrder?.orderId,
            reservationName = reservationName,
            reservationTime = reservationTime,
            occupiedSince = occupiedSince
        )
    }

    fun com.nexus.restaurant.data.local.TableEntity.toDomain(currentOrder: Order? = null): Table {
        return Table(
            tableNo = tableNo,
            status = TableStatus.valueOf(status),
            capacity = capacity,
            currentOrder = currentOrder,
            reservationName = reservationName,
            reservationTime = reservationTime,
            occupiedSince = occupiedSince
        )
    }
}

data class OrderDto(
    val orderId: String,
    val tableNo: String,
    val people: Int,
    val items: List<OrderItemDto>,
    val status: String,
    val notes: String,
    val timestamp: Long,
    val totalAmount: Double
)

data class OrderItemDto(
    val itemId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val notes: String
)

data class TableDto(
    val tableNo: String,
    val status: String,
    val capacity: Int,
    val currentOrder: String?,
    val reservationName: String?,
    val reservationTime: Long?,
    val occupiedSince: Long?
)
