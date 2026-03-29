package com.nexus.restaurant.presentation.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexus.restaurant.domain.model.UserRole
import com.nexus.restaurant.presentation.common.StatusIndicator
import com.nexus.restaurant.presentation.theme.GlassCardColor
import com.nexus.restaurant.presentation.theme.Primary
import com.nexus.restaurant.presentation.theme.PrimaryDark
import com.nexus.restaurant.presentation.theme.PrimaryLight
import com.nexus.restaurant.presentation.theme.ReadyColor
import com.nexus.restaurant.presentation.theme.ReservedRed

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    var username by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Handle login result
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginResult.Success -> {
                selectedRole?.let { role ->
                    onLoginSuccess(role.name.lowercase())
                }
                viewModel.clearLoginState()
            }
            is LoginResult.Error -> {
                errorMessage = (loginState as LoginResult.Error).message
                showErrorDialog = true
                viewModel.clearLoginState()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E9),
                        Color(0xFFB2DFDB),
                        Color(0xFF80CBC4)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Primary, PrimaryDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nexus Restaurant",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Text(
                text = "Management System",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF388E3C)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Connection Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(
                    color = if (isConnected) ReadyColor.copy(alpha = 0.15f) else ReservedRed.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                ).padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                StatusIndicator(isConnected = isConnected)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isConnected) "Connected to server" else "Connecting to server...",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isConnected) ReadyColor else ReservedRed
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Select Your Role",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(24.dp))

            RoleCard(
                icon = Icons.Default.Person,
                title = "Waiter",
                description = "Manage tables and orders",
                isSelected = selectedRole == UserRole.WAITER,
                onClick = { viewModel.setRole(UserRole.WAITER) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                icon = Icons.Default.Storefront,
                title = "Kitchen",
                description = "Process orders",
                isSelected = selectedRole == UserRole.KITCHEN,
                onClick = { viewModel.setRole(UserRole.KITCHEN) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                icon = Icons.Default.Restaurant,
                title = "Cashier",
                description = "Billing and payments",
                isSelected = selectedRole == UserRole.CASHIER,
                onClick = { viewModel.setRole(UserRole.CASHIER) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Username field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                placeholder = { Text("Enter your name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Primary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedLabelColor = Primary,
                    cursorColor = Primary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(username.ifBlank { "User" })
                },
                enabled = selectedRole != null && username.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                )
            ) {
                if (loginState == LoginResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Connecting...",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null
                    )
                }
            }
        }

        // Loading Overlay
        if (loginState == LoginResult.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(32.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Connecting to server...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "192.168.1.8:3000",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Error Dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = {
                    Text(
                        text = "Login Failed",
                        fontWeight = FontWeight.Bold,
                        color = ReservedRed
                    )
                },
                text = {
                    Text(errorMessage)
                },
                confirmButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK", color = Primary)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
private fun RoleCard(
    icon: ImageVector,
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Primary.copy(alpha = 0.15f) else GlassCardColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "backgroundColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Primary else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "borderColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(
                elevation = if (isSelected) 12.dp else 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = if (isSelected) Primary else Color.Black.copy(alpha = 0.1f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Primary else PrimaryLight.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Primary else Color(0xFF212121)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575)
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
