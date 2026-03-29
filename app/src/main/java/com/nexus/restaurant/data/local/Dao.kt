package com.nexus.restaurant.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY timestamp DESC")
    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun getOrderById(orderId: String): Flow<OrderEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("DELETE FROM orders WHERE orderId = :orderId")
    suspend fun deleteOrder(orderId: String)

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}

@Dao
interface TableDao {
    @Query("SELECT * FROM tables")
    fun getAllTables(): Flow<List<TableEntity>>

    @Query("SELECT * FROM tables WHERE tableNo = :tableNo")
    fun getTableById(tableNo: String): Flow<TableEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: TableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTables(tables: List<TableEntity>)

    @Update
    suspend fun updateTable(table: TableEntity)

    @Query("DELETE FROM tables")
    suspend fun deleteAllTables()
}

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_items WHERE category = :category")
    fun getMenuItemsByCategory(category: String): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(items: List<MenuItemEntity>)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAllMenuItems()
}
