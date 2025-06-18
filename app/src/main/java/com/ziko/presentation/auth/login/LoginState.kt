package com.ziko.presentation.auth.login

/**
 * Represents the UI state for the login screen.
 *
 * This sealed class encapsulates all possible states during the login process, enabling
 * a unidirectional state management pattern within the [LoginViewModel] and the
 * [LoginScreen] composable.
 *
 * ## Usage
 * This state is used to trigger UI feedback like showing a loading spinner, displaying
 * success navigation, or showing error messages.
 *
 * Example:
 * ```
 * when (val state = loginViewModel.loginState.value) {
 *     is LoginState.Idle -> { /* Default screen UI */ }
 *     is LoginState.Loading -> { CircularProgressIndicator() }
 *     is LoginState.Success -> { navController.navigate(Screen.Home.route) }
 *     is LoginState.Error -> { Text(state.message) }
 * }
 * ```
 *
 * @see LoginViewModel
 * @see LoginScreen
 */
sealed class LoginState {

    /**
     * Represents the initial or default state of the login screen.
     * No login operation is currently in progress.
     */
    data object Idle : LoginState()

    /**
     * Indicates that the login request is currently in progress.
     * Used to show loading indicators or disable the login button.
     */
    data object Loading : LoginState()

    /**
     * Represents a successful login operation.
     *
     * @property data The authentication token or result returned from the backend.
     */
    data class Success(val data: String) : LoginState()

    /**
     * Represents a failed login attempt.
     *
     * @property message A user-friendly error message explaining the failure reason.
     */
    data class Error(val message: String) : LoginState()
}
