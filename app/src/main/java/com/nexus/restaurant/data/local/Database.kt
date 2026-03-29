package com.nexus.restaurant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [OrderEntity::class, TableEntity::class, MenuItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun tableDao(): TableDao
    abstract fun menuItemDao(): MenuItemDao
}
