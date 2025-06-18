package com.ziko.presentation.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.model.SignUpData
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.data.remote.request.SignUpRequest
import com.ziko.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



/**
 * ViewModel responsible for managing the sign-up logic and state.
 *
 * @property authUseCase Handles authentication-related operations (e.g., sign-up API).
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    /** Stores user-entered sign-up form data */
    val signUpData = mutableStateOf(SignUpData())

    /** Stores email validation error message, null if valid */
    val emailError = mutableStateOf<String?>(null)

    /** True if password and confirm password fields do not match */
    val passwordMismatchError = mutableStateOf(false)

    /** Backing state for exposing sign-up UI state */
    private val _signUpState = mutableStateOf<SignUpState>(SignUpState.Idle)

    /** Public immutable version of the current sign-up state */
    val signUpState: State<SignUpState> = _signUpState

    /** Sign-up method being used, e.g., "password", "google" etc. */
    private val authMethod = "password"

    /** Updates the user's first name */
    fun updateFirstName(firstName: String) {
        signUpData.value.firstName = firstName
    }

    /** Updates the user's last name */
    fun updateLastName(lastName: String) {
        signUpData.value.lastName = lastName
    }

    /**
     * Updates the user's email and clears any previous email error
     */
    fun updateEmail(email: String) {
        signUpData.value.email = email
        emailError.value = null
    }

    /**
     * Updates the user's password and clears mismatch error
     */
    fun updatePassword(password: String) {
        signUpData.value.password = password
        passwordMismatchError.value = false
    }

    /**
     * Updates the user's confirm password field and clears mismatch error
     */
    fun updateConfirmPassword(confirmPassword: String) {
        signUpData.value.confirmPassword = confirmPassword
        passwordMismatchError.value = false
    }

    /**
     * Validates the email using a simple regex.
     *
     * @return true if valid, false otherwise.
     */
    fun isEmailValid(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        val isValid = signUpData.value.email.matches(emailPattern)
        if (!isValid) {
            emailError.value = "Invalid email format"
        }
        return isValid
    }

    /**
     * Returns a string representation of the current form data.
     */
    fun returnValues(): String {
        return "$signUpData"
    }

    /**
     * Checks whether the password and confirm password match.
     *
     * @return true if they match, false otherwise.
     */
    private fun arePasswordsMatching(): Boolean {
        val matching = signUpData.value.password == signUpData.value.confirmPassword
        passwordMismatchError.value = !matching
        return matching
    }

    /**
     * Attempts to perform user sign-up.
     *
     * Triggers the UI loading state and calls the appropriate callbacks
     * depending on whether the sign-up is successful or fails.
     *
     * @param onSuccess Callback to invoke on successful sign-up.
     * @param onError Callback to invoke on error with an error message.
     */
    fun signup(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!isEmailValid()) return
        if (!arePasswordsMatching()) return

        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading

            val signUpRequest = SignUpRequest(
                firstName = signUpData.value.firstName,
                lastName = signUpData.value.lastName,
                email = signUpData.value.email,
                password = signUpData.value.password,
                authMethod = authMethod
            )

            when (val result = authUseCase.signup(signUpRequest)) {
                is Resource.Success -> {
                    _signUpState.value = SignUpState.Success
                    onSuccess()
                }
                is Resource.Error -> {
                    _signUpState.value = SignUpState.Error(result.message ?: "Sign-up failed")
                    onError(result.message ?: "Sign-up failed")
                }
                is Resource.Loading -> {
                    // No-op: already set loading state above
                }
            }
        }
    }
}
