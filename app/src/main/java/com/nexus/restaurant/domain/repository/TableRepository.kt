package com.nexus.restaurant.domain.repository

import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import kotlinx.coroutines.flow.Flow

interface TableRepository {
    fun getTables(): Flow<List<Table>>
    fun getTableById(tableNo: String): Flow<Table?>
    suspend fun updateTableStatus(tableNo: String, status: TableStatus): Result<Table>
    suspend fun reserveTable(tableNo: String, customerName: String, reservationTime: Long): Result<Table>
    suspend fun clearTable(tableNo: String): Result<Table>
    suspend fun syncTables(): Result<Unit>
}
