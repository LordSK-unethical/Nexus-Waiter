package com.nexus.restaurant.presentation.kitchen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.presentation.common.GradientBackground
import com.nexus.restaurant.presentation.common.StatusIndicator
import com.nexus.restaurant.presentation.theme.GlassCardColor
import com.nexus.restaurant.presentation.theme.OccupiedOrange
import com.nexus.restaurant.presentation.theme.PreparingColor
import com.nexus.restaurant.presentation.theme.Primary
import com.nexus.restaurant.presentation.theme.ReadyColor
import com.nexus.restaurant.presentation.theme.ReservedRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchenScreen(
    viewModel: KitchenViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    val pendingOrders = orders.filter { it.status == OrderStatus.PENDING }
    val preparingOrders = orders.filter { it.status == OrderStatus.PREPARING }
    val readyOrders = orders.filter { it.status == OrderStatus.READY }

    GradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Kitchen",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${orders.size} active orders",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            StatusIndicator(isConnected = isConnected)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isConnected) "Live" else "Offline",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Pending Section
                item {
                    OrderSection(
                        title = "New Orders",
                        count = pendingOrders.size,
                        color = ReservedRed,
                        orders = pendingOrders,
                        onUpdateStatus = { order, newStatus ->
                            viewModel.updateOrderStatus(order, newStatus)
                        }
                    )
                }

                // Preparing Section
                item {
                    OrderSection(
                        title = "Preparing",
                        count = preparingOrders.size,
                        color = PreparingColor,
                        orders = preparingOrders,
                        onUpdateStatus = { order, newStatus ->
                            viewModel.updateOrderStatus(order, newStatus)
                        }
                    )
                }

                // Ready Section
                item {
                    OrderSection(
                        title = "Ready to Serve",
                        count = readyOrders.size,
                        color = ReadyColor,
                        orders = readyOrders,
                        showGlow = true,
                        onUpdateStatus = { order, newStatus ->
                            viewModel.updateOrderStatus(order, newStatus)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderSection(
    title: String,
    count: Int,
    color: Color,
    orders: List<Order>,
    showGlow: Boolean = false,
    onUpdateStatus: (Order, OrderStatus) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (orders.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GlassCardColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No orders",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        } else {
            orders.forEach { order ->
                KitchenOrderCard(
                    order = order,
                    statusColor = color,
                    showGlow = showGlow && order.status == OrderStatus.READY,
                    onUpdateStatus = onUpdateStatus
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun KitchenOrderCard(
    order: Order,
    statusColor: Color,
    showGlow: Boolean,
    onUpdateStatus: (Order, OrderStatus) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val progress = when (order.status) {
        OrderStatus.PENDING -> 0.15f
        OrderStatus.PREPARING -> 0.5f
        OrderStatus.READY -> 0.85f
        OrderStatus.SERVED -> 1f
        OrderStatus.CANCELLED -> 0f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (showGlow) 16.dp else 8.dp,
                spotColor = if (showGlow) ReadyColor.copy(alpha = glowAlpha) else statusColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (showGlow) 
                GlassCardColor.copy(alpha = 0.95f) 
            else 
                GlassCardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(statusColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Table ${order.tableNo}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = getTimeAgo(order.timestamp),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // Status Badge
                StatusBadge(status = order.status, color = statusColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Items
            order.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.quantity}x ${item.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (item.notes.isNotEmpty()) {
                        Text(
                            text = item.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = OccupiedOrange
                        )
                    }
                }
            }

            if (order.notes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: ${order.notes}",
                    style = MaterialTheme.typography.bodySmall,
                    color = ReservedRed,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = statusColor,
                trackColor = statusColor.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (order.status) {
                    OrderStatus.PENDING -> {
                        ActionButton(
                            text = "Start Preparing",
                            color = PreparingColor,
                            onClick = { onUpdateStatus(order, OrderStatus.PREPARING) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OrderStatus.PREPARING -> {
                        ActionButton(
                            text = "Mark Ready",
                            color = ReadyColor,
                            onClick = { onUpdateStatus(order, OrderStatus.READY) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OrderStatus.READY -> {
                        ActionButton(
                            text = "Served",
                            color = Primary,
                            onClick = { onUpdateStatus(order, OrderStatus.SERVED) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: OrderStatus, color: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))
    ) {
        Text(
            text = status.displayName(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (text == "Mark Ready" || text == "Served") {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

private fun getTimeAgo(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val minutes = diff / (1000 * 60)
    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "${minutes}m ago"
        else -> "${minutes / 60}h ${minutes % 60}m"
    }
}
