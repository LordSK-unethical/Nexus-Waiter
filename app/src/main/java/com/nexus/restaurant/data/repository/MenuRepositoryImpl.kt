package com.nexus.restaurant.data.repository

import com.nexus.restaurant.data.local.MenuItemDao
import com.nexus.restaurant.data.local.MenuItemEntity
import com.nexus.restaurant.domain.model.MenuItem
import com.nexus.restaurant.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor(
    private val menuItemDao: MenuItemDao
) : MenuRepository {

    override fun getMenuItems(): Flow<List<MenuItem>> {
        return menuItemDao.getAllMenuItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMenuItemsByCategory(category: String): Flow<List<MenuItem>> {
        return menuItemDao.getMenuItemsByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshMenuItems(): Result<Unit> {
        return try {
            // Insert sample menu items if database is empty
            val sampleItems = getSampleMenuItems()
            menuItemDao.insertMenuItems(sampleItems.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getSampleMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("1", "Burger", "Juicy beef burger", 12.99, "Main"),
            MenuItem("2", "Pizza", "Margherita pizza", 14.99, "Main"),
            MenuItem("3", "Pasta", "Creamy Alfredo pasta", 11.99, "Main"),
            MenuItem("4", "Salad", "Fresh garden salad", 8.99, "Starter"),
            MenuItem("5", "Soup", "Tomato soup", 6.99, "Starter"),
            MenuItem("6", "Fries", "Crispy fries", 4.99, "Starter"),
            MenuItem("7", "Cola", "Cold cola", 2.99, "Drinks"),
            MenuItem("8", "Coffee", "Hot coffee", 3.99, "Drinks"),
            MenuItem("9", "Cake", "Chocolate cake", 7.99, "Dessert"),
            MenuItem("10", "Ice Cream", "Vanilla ice cream", 5.99, "Dessert")
        )
    }
}

private fun MenuItemEntity.toDomain(): MenuItem {
    return MenuItem(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl,
        isAvailable = isAvailable
    )
}

private fun MenuItem.toEntity(): MenuItemEntity {
    return MenuItemEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl,
        isAvailable = isAvailable
    )
}
