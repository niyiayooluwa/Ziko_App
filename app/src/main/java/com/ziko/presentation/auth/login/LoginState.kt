package com.ziko.presentation.auth.login

sealed class LoginState {
    data object Idle : LoginState()    // Initial state
    data object Loading : LoginState() // When the login request is in progress
    data class Success(val data: String) : LoginState() // On successful login
    data class Error(val message: String) : LoginState() // On login error
}
