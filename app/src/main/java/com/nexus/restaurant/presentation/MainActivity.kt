package com.nexus.restaurant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nexus.restaurant.presentation.navigation.NexusNavHost
import com.nexus.restaurant.presentation.theme.NexusRestaurantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusRestaurantTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NexusNavHost()
                }
            }
        }
    }
}
