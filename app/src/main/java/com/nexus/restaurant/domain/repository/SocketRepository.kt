package com.nexus.restaurant.domain.repository

import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    val socketEvents: Flow<SocketEvent>
    val isConnected: Flow<Boolean>
    fun connect(serverUrl: String, token: String? = null)
    fun disconnect()
    fun registerUser(role: UserRole, userId: String)
    fun emitNewOrder(order: Order)
    fun emitOrderStatusUpdate(orderId: String, status: String)
    fun emitCancelOrder(orderId: String)
    fun emitTableUpdate(table: Table)
    fun requestSync()
}
