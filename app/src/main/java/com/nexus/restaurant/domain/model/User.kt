package com.nexus.restaurant.domain.model

enum class UserRole {
    WAITER,
    KITCHEN,
    CASHIER;

    fun displayName(): String = when (this) {
        WAITER -> "Waiter"
        KITCHEN -> "Kitchen"
        CASHIER -> "Cashier"
    }
}

data class User(
    val id: String,
    val name: String,
    val role: UserRole,
    val token: String? = null
)
