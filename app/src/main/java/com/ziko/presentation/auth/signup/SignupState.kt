package com.ziko.presentation.auth.signup

sealed class SignUpState {
    data object Idle : SignUpState()         // Initial state
    data object Loading : SignUpState()      // When the sign-up request is in progress
    data object Success : SignUpState()      // On successful sign-up
    data class Error(val message: String) : SignUpState() // On sign-up error
}