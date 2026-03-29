package com.nexus.restaurant.data.repository

import com.nexus.restaurant.data.socket.SocketManager
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.UserRole
import com.nexus.restaurant.domain.repository.SocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketRepositoryImpl @Inject constructor(
    private val socketManager: SocketManager
) : SocketRepository {

    override val socketEvents: Flow<SocketEvent>
        get() = socketManager.socketEvents

    override val isConnected: Flow<Boolean>
        get() = socketManager.isConnected

    override fun connect(serverUrl: String, token: String?) {
        socketManager.connect(serverUrl, token)
    }

    override fun disconnect() {
        socketManager.disconnect()
    }

    override fun registerUser(role: UserRole, userId: String) {
        socketManager.registerUser(role, userId)
    }

    override fun emitNewOrder(order: Order) {
        socketManager.emitNewOrder(order)
    }

    override fun emitOrderStatusUpdate(orderId: String, status: String) {
        socketManager.emitOrderStatusUpdate(orderId, status)
    }

    override fun emitCancelOrder(orderId: String) {
        socketManager.emitCancelOrder(orderId)
    }

    override fun emitTableUpdate(table: Table) {
        socketManager.emitTableUpdate(table)
    }

    override fun requestSync() {
        socketManager.requestSync()
    }
}
