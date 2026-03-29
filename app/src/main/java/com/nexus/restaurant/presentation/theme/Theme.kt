package com.nexus.restaurant.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,
    secondary = Secondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = TextPrimary,
    tertiary = OccupiedOrange,
    onTertiary = TextOnPrimary,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = GlassWhite,
    onSurfaceVariant = TextSecondary,
    error = ReservedRed,
    onError = TextOnPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = TextPrimary,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = TextOnPrimary,
    secondary = SecondaryLight,
    onSecondary = TextPrimary,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = TextOnPrimary,
    tertiary = OccupiedOrange,
    onTertiary = TextPrimary,
    background = BackgroundDark,
    onBackground = TextOnPrimary,
    surface = SurfaceDark,
    onSurface = TextOnPrimary,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = TextSecondary,
    error = ReservedRed,
    onError = TextOnPrimary
)

@Composable
fun NexusRestaurantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
