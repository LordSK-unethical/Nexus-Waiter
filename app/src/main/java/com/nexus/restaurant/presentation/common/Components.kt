package com.nexus.restaurant.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexus.restaurant.presentation.theme.GlassCardColor
import com.nexus.restaurant.presentation.theme.GlassWhite
import com.nexus.restaurant.presentation.theme.Primary
import com.nexus.restaurant.presentation.theme.ShadowLight

@Composable
fun GlassSurfaceCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 28.dp,
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = elevation,
                spotColor = ShadowLight,
                shape = RoundedCornerShape(cornerRadius)
            ),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = GlassCardColor
        )
    ) {
        content()
    }
}

@Composable
fun StatusIndicator(
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    val dotColor by animateColorAsState(
        targetValue = if (isConnected) Color(0xFF4CAF50) else Color(0xFFE57373),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "statusColor"
    )

    val dotSize by animateDpAsState(
        targetValue = if (isConnected) 12.dp else 10.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "statusSize"
    )

    Box(
        modifier = modifier
            .size(dotSize)
            .clip(CircleShape)
            .background(dotColor)
    )
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE8E8E8),
                        Color(0xFFF0F0F0)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = Primary,
            modifier = Modifier.size(48.dp)
        )
    }
}
