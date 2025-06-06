package com.ziko.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.presentation.profile.UserViewModel
import com.ziko.util.DataStoreManager
import com.ziko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @javax.inject.Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisible by mutableStateOf(false)
        private set

    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState: State<LoginState> = _loginState

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onPasswordVisibilityToggle() {
        passwordVisible = !passwordVisible
    }

    fun login() {
        if (email.isBlank() || password.isBlank()) return

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            when (val result = authUseCase.login(email, password)) {
                is Resource.Success -> {
                    val loginResponse = result.data
                    val token = loginResponse?.data

                    if (token != null) {
                        dataStoreManager.saveToken(token)
                        _token.value = token
                        _loginState.value = LoginState.Success(token)
                    } else {
                        _loginState.value = LoginState.Error("Token missing from response")
                    }
                }
                is Resource.Error -> {
                    _loginState.value = LoginState.Error(result.message ?: "Unknown Error")
                }
                is Resource.Loading -> {
                    // Optional: Do nothing here
                }
            }
        }
    }
}

