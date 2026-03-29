package com.nexus.restaurant.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexus.restaurant.presentation.cashier.CashierScreen
import com.nexus.restaurant.presentation.kitchen.KitchenScreen
import com.nexus.restaurant.presentation.login.LoginScreen
import com.nexus.restaurant.presentation.waiter.WaiterScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Waiter : Screen("waiter")
    object Kitchen : Screen("kitchen")
    object Cashier : Screen("cashier")
}

@Composable
fun NexusNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { role ->
                    val destination = when (role) {
                        "waiter" -> Screen.Waiter.route
                        "kitchen" -> Screen.Kitchen.route
                        "cashier" -> Screen.Cashier.route
                        else -> Screen.Waiter.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Waiter.route) {
            WaiterScreen()
        }

        composable(Screen.Kitchen.route) {
            KitchenScreen()
        }

        composable(Screen.Cashier.route) {
            CashierScreen()
        }
    }
}
