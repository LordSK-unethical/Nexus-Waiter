package com.nexus.restaurant.data.repository

import com.nexus.restaurant.data.local.OrderDao
import com.nexus.restaurant.data.local.OrderEntity
import com.nexus.restaurant.data.socket.SocketManager
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val socketManager: SocketManager
) : OrderRepository {

    override fun getOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>> {
        return orderDao.getOrdersByStatus(status.name).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getOrderById(orderId: String): Flow<Order?> {
        return orderDao.getOrderById(orderId).map { it?.toDomain() }
    }

    override suspend fun createOrder(order: Order): Result<Order> {
        return try {
            orderDao.insertOrder(order.toEntity())
            socketManager.emitNewOrder(order)
            Result.success(order)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Order> {
        return try {
            val existingOrder = orderDao.getOrderById(orderId).first()
                ?: return Result.failure(Exception("Order not found"))

            val updatedOrder = existingOrder.copy(status = status.name)
            orderDao.updateOrder(updatedOrder)

            socketManager.emitOrderStatusUpdate(orderId, status.name)
            Result.success(updatedOrder.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelOrder(orderId: String): Result<Unit> {
        return try {
            orderDao.deleteOrder(orderId)
            socketManager.emitCancelOrder(orderId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncOrders(): Result<Unit> {
        return try {
            socketManager.requestSync()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun Order.toEntity(): OrderEntity {
    val gson = com.google.gson.Gson()
    return OrderEntity(
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

private fun OrderEntity.toDomain(): Order {
    val gson = com.google.gson.Gson()
    val itemType = object : com.google.gson.reflect.TypeToken<List<com.nexus.restaurant.domain.model.OrderItem>>() {}.type
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
