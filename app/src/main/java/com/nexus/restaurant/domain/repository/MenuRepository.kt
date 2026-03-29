package com.nexus.restaurant.domain.repository

import com.nexus.restaurant.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenuItems(): Flow<List<MenuItem>>
    fun getMenuItemsByCategory(category: String): Flow<List<MenuItem>>
    suspend fun refreshMenuItems(): Result<Unit>
}
