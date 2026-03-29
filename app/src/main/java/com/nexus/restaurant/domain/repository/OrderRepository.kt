package com.nexus.restaurant.domain.repository

import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>>
    fun getOrderById(orderId: String): Flow<Order?>
    suspend fun createOrder(order: Order): Result<Order>
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Order>
    suspend fun cancelOrder(orderId: String): Result<Unit>
    suspend fun syncOrders(): Result<Unit>
}
