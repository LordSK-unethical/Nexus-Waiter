package com.nexus.restaurant.data.repository

import com.nexus.restaurant.data.local.TableDao
import com.nexus.restaurant.data.local.TableEntity
import com.nexus.restaurant.data.socket.SocketManager
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import com.nexus.restaurant.domain.repository.TableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepositoryImpl @Inject constructor(
    private val tableDao: TableDao,
    private val socketManager: SocketManager
) : TableRepository {

    override fun getTables(): Flow<List<Table>> {
        return tableDao.getAllTables().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTableById(tableNo: String): Flow<Table?> {
        return tableDao.getTableById(tableNo).map { it?.toDomain() }
    }

    override suspend fun updateTableStatus(tableNo: String, status: TableStatus): Result<Table> {
        return try {
            val existingTable = tableDao.getTableById(tableNo).first()
                ?: return Result.failure(Exception("Table not found"))

            val updatedTable = existingTable.copy(
                status = status.name,
                occupiedSince = if (status == TableStatus.OCCUPIED) System.currentTimeMillis() else null,
                reservationName = if (status == TableStatus.AVAILABLE) null else existingTable.reservationName,
                reservationTime = if (status == TableStatus.AVAILABLE) null else existingTable.reservationTime
            )
            tableDao.updateTable(updatedTable)

            val domainTable = updatedTable.toDomain()
            socketManager.emitTableUpdate(domainTable)
            Result.success(domainTable)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun reserveTable(
        tableNo: String,
        customerName: String,
        reservationTime: Long
    ): Result<Table> {
        return try {
            val existingTable = tableDao.getTableById(tableNo).first()
                ?: return Result.failure(Exception("Table not found"))

            val updatedTable = existingTable.copy(
                status = TableStatus.RESERVED.name,
                reservationName = customerName,
                reservationTime = reservationTime
            )
            tableDao.updateTable(updatedTable)

            val domainTable = updatedTable.toDomain()
            socketManager.emitTableUpdate(domainTable)
            Result.success(domainTable)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearTable(tableNo: String): Result<Table> {
        return try {
            val existingTable = tableDao.getTableById(tableNo).first()
                ?: return Result.failure(Exception("Table not found"))

            val updatedTable = existingTable.copy(
                status = TableStatus.AVAILABLE.name,
                currentOrderId = null,
                reservationName = null,
                reservationTime = null,
                occupiedSince = null
            )
            tableDao.updateTable(updatedTable)

            val domainTable = updatedTable.toDomain()
            socketManager.emitTableUpdate(domainTable)
            Result.success(domainTable)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncTables(): Result<Unit> {
        return try {
            socketManager.requestSync()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun TableEntity.toDomain(): Table {
    return Table(
        tableNo = tableNo,
        status = TableStatus.valueOf(status),
        capacity = capacity,
        currentOrder = null,
        reservationName = reservationName,
        reservationTime = reservationTime,
        occupiedSince = occupiedSince
    )
}
