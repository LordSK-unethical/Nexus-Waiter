package com.nexus.restaurant.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.restaurant.domain.model.SocketEvent
import com.nexus.restaurant.domain.model.UserRole
import com.nexus.restaurant.domain.repository.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginResult {
    object Loading : LoginResult()
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginResult?>(null)
    val loginState: StateFlow<LoginResult?> = _loginState.asStateFlow()

    private val _selectedRole = MutableStateFlow<UserRole?>(null)
    val selectedRole: StateFlow<UserRole?> = _selectedRole.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    init {
        viewModelScope.launch {
            socketRepository.isConnected.collect { connected ->
                _isConnected.value = connected
                if (connected && _loginState.value == LoginResult.Loading) {
                    // Connection successful, now try to login
                    tryLogin()
                }
            }
        }

        viewModelScope.launch {
            socketRepository.socketEvents.collect { event ->
                when (event) {
                    is SocketEvent.Connected -> {
                        if (_loginState.value == LoginResult.Loading) {
                            tryLogin()
                        }
                    }
                    is SocketEvent.Error -> {
                        _loginState.value = LoginResult.Error(event.message)
                    }
                    is SocketEvent.Notification -> {
                        if (event.message.contains("authenticated", ignoreCase = true) ||
                            event.message.contains("welcome", ignoreCase = true)) {
                            _loginState.value = LoginResult.Success
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun setRole(role: UserRole) {
        _selectedRole.value = role
    }

    fun login(username: String) {
        val role = _selectedRole.value ?: return

        _loginState.value = LoginResult.Loading

        // Connect to server
        socketRepository.connect("http://192.168.1.8:3000")
        
        // Register after connection
        viewModelScope.launch {
            // Wait for connection or timeout
            kotlinx.coroutines.delay(3000)
            if (_loginState.value == LoginResult.Loading) {
                // If still loading after timeout, try with current connection state
                tryLogin()
            }
        }
    }

    private fun tryLogin() {
        val role = _selectedRole.value ?: return
        val username = "user_${System.currentTimeMillis()}"
        
        socketRepository.registerUser(role, username)
        
        // Give time for server response
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            if (_loginState.value == LoginResult.Loading) {
                // If no response, assume success if connected
                if (_isConnected.value) {
                    _loginState.value = LoginResult.Success
                } else {
                    _loginState.value = LoginResult.Error("Could not connect to server")
                }
            }
        }
    }

    fun clearLoginState() {
        _loginState.value = null
    }

    override fun onCleared() {
        super.onCleared()
    }
}
