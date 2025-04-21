package com.ziko.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @javax.inject.Inject constructor (private val authUseCase: AuthUseCase) : ViewModel() {

    // Mutable state to handle different login states (Idle, Loading, Success, Error)
    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState: State<LoginState> = _loginState

    // Function to handle the login logic
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            when (val result = authUseCase.login(email, password)) {
                is Resource.Success -> {
                    val token = result.data ?: ""
                    _loginState.value = LoginState.Success(token.toString())}
                is Resource.Error -> _loginState.value = LoginState.Error(result.message ?: "Unknown Error")
                is Resource.Loading -> {}
            }
        }
    }
}
