package com.nexus.restaurant.domain.model

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true
)

data class MenuCategory(
    val name: String,
    val items: List<MenuItem>
)
