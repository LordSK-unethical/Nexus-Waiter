package com.nexus.restaurant.presentation.waiter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexus.restaurant.domain.model.MenuItem
import com.nexus.restaurant.domain.model.Order
import com.nexus.restaurant.domain.model.OrderItem
import com.nexus.restaurant.domain.model.OrderStatus
import com.nexus.restaurant.domain.model.Table
import com.nexus.restaurant.domain.model.TableStatus
import com.nexus.restaurant.presentation.common.GlassSurfaceCard
import com.nexus.restaurant.presentation.common.GradientBackground
import com.nexus.restaurant.presentation.common.StatusIndicator
import com.nexus.restaurant.presentation.theme.AvailableGreen
import com.nexus.restaurant.presentation.theme.AvailableGreenLight
import com.nexus.restaurant.presentation.theme.GlassCardColor
import com.nexus.restaurant.presentation.theme.OccupiedOrange
import com.nexus.restaurant.presentation.theme.OccupiedOrangeLight
import com.nexus.restaurant.presentation.theme.Primary
import com.nexus.restaurant.presentation.theme.PrimaryDark
import com.nexus.restaurant.presentation.theme.PrimaryLight
import com.nexus.restaurant.presentation.theme.ReadyColor
import com.nexus.restaurant.presentation.theme.ReservedRed
import com.nexus.restaurant.presentation.theme.ReservedRedLight
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaiterScreen(
    viewModel: WaiterViewModel = hiltViewModel()
) {
    val tables by viewModel.tables.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showOrderSheet by remember { mutableStateOf(false) }
    var showCartSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    GradientBackground {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = getGreeting(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Waiter Dashboard",
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
                                text = if (isConnected) "Connected" else "Offline",
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
            floatingActionButton = {
                if (cartItems.isNotEmpty()) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = ReservedRed,
                                contentColor = Color.White
                            ) {
                                Text(cartItems.values.sumOf { it }.toString())
                            }
                        }
                    ) {
                        FloatingActionButton(
                            onClick = { showCartSheet = true },
                            containerColor = Primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                    }
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Active Orders Summary
                ActiveOrdersBar(orders = orders)

                Spacer(modifier = Modifier.height(16.dp))

                // Tables Grid
                Text(
                    text = "Tables",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tables) { table ->
                        TableCard(
                            table = table,
                            onClick = {
                                selectedTable = table
                                showOrderSheet = true
                            }
                        )
                    }
                }
            }
        }

        // Order Bottom Sheet
        if (showOrderSheet && selectedTable != null) {
            ModalBottomSheet(
                onDismissRequest = { showOrderSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                OrderCreationSheet(
                    table = selectedTable!!,
                    menuItems = viewModel.getSampleMenuItems(),
                    cartItems = cartItems,
                    onAddToCart = { item, quantity ->
                        viewModel.addToCart(item, quantity)
                        scope.launch {
                            snackbarHostState.showSnackbar("Added ${item.name} x$quantity")
                        }
                    },
                    onPlaceOrder = {
                        viewModel.createOrder(selectedTable!!)
                        showOrderSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Order placed successfully!")
                        }
                    }
                )
            }
        }

        // Cart Bottom Sheet
        if (showCartSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCartSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                CartSummary(
                    items = cartItems,
                    tableNumber = selectedTable?.tableNo ?: "",
                    onRemoveItem = { viewModel.removeFromCart(it) },
                    onClearCart = { viewModel.clearCart() },
                    onCheckout = {
                        selectedTable?.let { viewModel.createOrder(it) }
                        showCartSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Order placed!")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ActiveOrdersBar(orders: List<Order>) {
    val preparingCount = orders.count { it.status == OrderStatus.PREPARING }
    val readyCount = orders.count { it.status == OrderStatus.READY }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OrderStatusCard(
            title = "Preparing",
            count = preparingCount,
            color = OccupiedOrange,
            modifier = Modifier.weight(1f)
        )
        OrderStatusCard(
            title = "Ready",
            count = readyCount,
            color = ReadyColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun OrderStatusCard(
    title: String,
    count: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
private fun TableCard(
    table: Table,
    onClick: () -> Unit
) {
    val statusColor by animateColorAsState(
        targetValue = when (table.status) {
            TableStatus.AVAILABLE -> AvailableGreen
            TableStatus.OCCUPIED -> OccupiedOrange
            TableStatus.RESERVED -> ReservedRed
        },
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "statusColor"
    )

    val statusLightColor by animateColorAsState(
        targetValue = when (table.status) {
            TableStatus.AVAILABLE -> AvailableGreenLight
            TableStatus.OCCUPIED -> OccupiedOrangeLight
            TableStatus.RESERVED -> ReservedRedLight
        },
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "statusLightColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                spotColor = statusColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GlassCardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                statusLightColor,
                                statusColor.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .border(
                        width = 3.dp,
                        color = statusColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = table.tableNo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = table.status.displayName(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = statusColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${table.capacity} seats",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            if (table.status == TableStatus.OCCUPIED && table.occupiedSince != null) {
                Spacer(modifier = Modifier.height(8.dp))
                val duration = getDurationString(table.occupiedSince)
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = OccupiedOrange,
                    fontWeight = FontWeight.Medium
                )
            }

            if (table.status == TableStatus.RESERVED && table.reservationName != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = table.reservationName!!,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = ReservedRed
                )
            }
        }
    }
}

@Composable
private fun OrderCreationSheet(
    table: Table,
    menuItems: List<MenuItem>,
    cartItems: Map<MenuItem, Int>,
    onAddToCart: (MenuItem, Int) -> Unit,
    onPlaceOrder: () -> Unit
) {
    val categories = menuItems.groupBy { it.category }
    var selectedCategory by remember { mutableStateOf(categories.keys.first()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Table ${table.tableNo} - New Order",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.keys.forEach { category ->
                CategoryChip(
                    text = category,
                    isSelected = category == selectedCategory,
                    onClick = { selectedCategory = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu items grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(300.dp)
        ) {
            items(categories[selectedCategory] ?: emptyList()) { item ->
                MenuItemCard(
                    item = item,
                    quantity = cartItems[item] ?: 0,
                    onAdd = { onAddToCart(item, 1) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Place Order Button
        if (cartItems.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Primary)
                    .clickable(onClick = onPlaceOrder)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Place Order (${cartItems.values.sumOf { it }} items)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary else Color.Gray.copy(alpha = 0.1f)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun MenuItemCard(
    item: MenuItem,
    quantity: Int,
    onAdd: () -> Unit
) {
    var itemQuantity by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(PrimaryLight.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Text(
                text = "$${String.format("%.2f", item.price)}",
                style = MaterialTheme.typography.bodySmall,
                color = Primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedContent(
                targetState = itemQuantity,
                transitionSpec = {
                    (fadeIn() + scaleIn()) togetherWith (fadeOut() + scaleOut())
                },
                label = "quantity"
            ) { qty ->
                if (qty > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = { if (itemQuantity > 0) itemQuantity-- },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = ReservedRed
                            )
                        }

                        Text(
                            text = qty.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { itemQuantity++ },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = Primary
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Primary)
                            .clickable { itemQuantity = 1 }
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Add",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CartSummary(
    items: Map<MenuItem, Int>,
    tableNumber: String,
    onRemoveItem: (MenuItem) -> Unit,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        val total = items.entries.sumOf { (item, qty) -> item.price * qty }

        items.forEach { (item, qty) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "$qty x $${String.format("%.2f", item.price)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "$${String.format("%.2f", item.price * qty)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format("%.2f", total)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Primary)
                .clickable(onClick = onCheckout)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Place Order",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private fun getGreeting(): String {
    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

private fun getDurationString(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val minutes = diff / (1000 * 60)
    val hours = minutes / 60
    return when {
        hours > 0 -> "${hours}h ${minutes % 60}m"
        else -> "${minutes}m"
    }
}
