package com.ziko.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.core.datastore.DataStoreManager
import com.ziko.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for handling user login operations and managing login UI state.
 *
 * This ViewModel implements state hoisting for email, password, and login result states.
 * It coordinates with the [AuthUseCase] for authentication and uses [DataStoreManager] to
 * persist the access token locally on successful login.
 *
 * ## State Management:
 * - `email`, `password`, and `passwordVisible` manage UI text inputs and visibility.
 * - `_loginState` drives login result feedback such as loading, success, or error.
 * - `_token` emits the stored access token once login succeeds.
 *
 * ## Usage:
 * ```
 * val loginViewModel: LoginViewModel = hiltViewModel()
 * val loginState = loginViewModel.loginState.value
 *
 * if (loginState is LoginState.Success) {
 *     val token = loginState.data
 *     // Navigate to home screen
 * }
 * ```
 *
 * @property authUseCase Business logic for authentication
 * @property dataStoreManager Handles token persistence
 *
 * @see LoginState
 * @see AuthUseCase
 */
@HiltViewModel
class LoginViewModel @javax.inject.Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    /**
     * The user's email input value.
     */
    var email by mutableStateOf("")
        private set

    /**
     * The user's password input value.
     */
    var password by mutableStateOf("")
        private set

    /**
     * Flag to toggle password visibility in the UI.
     */
    var passwordVisible by mutableStateOf(false)
        private set

    // Backing state for login result (Idle, Loading, Success, Error)
    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)

    /**
     * Publicly exposed immutable login state.
     */
    val loginState: State<LoginState> = _loginState

    // Emits access token after successful login
    private val _token = MutableStateFlow<String?>(null)

    /**
     * Access token flow for observing login result outside the viewmodel.
     */
    val token = _token.asStateFlow()

    /**
     * Updates the email state when the user types in the email field.
     *
     * @param newEmail The new email string input
     */
    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    /**
     * Updates the password state when the user types in the password field.
     *
     * @param newPassword The new password string input
     */
    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    /**
     * Toggles the password visibility state (show/hide password in UI).
     */
    fun onPasswordVisibilityToggle() {
        passwordVisible = !passwordVisible
    }

    /**
     * Initiates the login process using the provided email and password.
     *
     * Validates inputs, invokes the login use case, handles success/failure, and stores the token.
     * State transitions:
     * - Sets [LoginState.Loading] while login is in progress
     * - On success, saves token to datastore and emits [LoginState.Success]
     * - On failure, emits [LoginState.Error] with a descriptive message
     *
     * @throws IllegalStateException is not explicitly thrown but calling with blank inputs does nothing.
     */
    fun login() {
        // Guard clause: do nothing if fields are empty
        if (email.isBlank() || password.isBlank()) return

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            when (val result = authUseCase.login(email, password)) {
                is Resource.Success -> {
                    val loginResponse = result.data
                    val token = loginResponse?.data

                    if (token != null) {
                        // Save token and update success state
                        dataStoreManager.saveToken(token)
                        _token.value = token
                        _loginState.value = LoginState.Success(token)
                    } else {
                        _loginState.value = LoginState.Error("Token missing from response")
                    }
                }

                is Resource.Error -> {
                    // Emit error state with provided or fallback message
                    _loginState.value = LoginState.Error(result.message ?: "Unknown Error")
                }

                is Resource.Loading -> {
                    // No-op: already handled above
                }
            }
        }
    }
}


