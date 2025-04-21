package com.ziko.presentation.auth.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.model.SignUpData
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {
    private val signUpData = mutableStateOf(SignUpData())
    val emailError = mutableStateOf<String?>(null)
    val passwordMismatchError = mutableStateOf(false)
    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    private val authMethod = "email_password"

    fun updateFirstName(firstName: String) {
        signUpData.value.firstName = firstName
    }

    fun updateLastName(lastName: String) {
        signUpData.value.lastName = lastName
    }

    fun updateEmail(email: String) {
        signUpData.value.email = email
        emailError.value = null // Clear any previous email error
    }

    fun updatePassword(password: String) {
        signUpData.value.password = password
        passwordMismatchError.value = false // Clear password mismatch error
    }

    fun updateConfirmPassword(confirmPassword: String) {
        signUpData.value.confirmPassword = confirmPassword
        passwordMismatchError.value = false // Clear password mismatch error
    }

    fun isEmailValid(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        val isValid = signUpData.value.email.matches(emailPattern)
        if (!isValid) {
            emailError.value = "Invalid email format"
        }
        return isValid
    }

    private fun arePasswordsMatching(): Boolean {
        val matching = signUpData.value.password == signUpData.value.confirmPassword
        passwordMismatchError.value = !matching
        return matching
    }

    // Function to send sign-up data to the backend
    fun signup(onSuccess: () -> Unit, onError: (String) -> Unit) {
        println("1difi")
        //if (!isEmailValid()) return
        //if (!arePasswordsMatching()) return
        println("2difi")
        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading

            val signUpRequest = SignUpRequest(
                firstName = signUpData.value.firstName,
                lastName = signUpData.value.lastName,
                email = signUpData.value.email,
                password = signUpData.value.password,
                authMethod = authMethod
            )

            println("3difi")
            when (val result = authUseCase.signup(signUpRequest)) {
                is Resource.Success -> {
                    _signUpState.value = SignUpState.Success
                    onSuccess()
                    println("4difi")
                }
                is Resource.Error -> {
                    _signUpState.value = SignUpState.Error(result.message ?: "Sign-up failed")
                    onError(result.message ?: "Sign-up failed")
                    println("5difi")
                }
                is Resource.Loading -> {
                    // Already set to Loading at the beginning of the function
                println("6difi")
                }
            }
        }
    }
}