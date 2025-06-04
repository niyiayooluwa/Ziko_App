package com.ziko.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.remote.ProfileResponse
import com.ziko.data.remote.UserData
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/*{@HiltViewModel
class UserViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel()
{

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _userError = MutableStateFlow<String?>(null)
    val userError = _userError.asStateFlow()

    private val _tokenExpired = MutableStateFlow(false)
    val tokenExpired = _tokenExpired.asStateFlow()

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val response = authUseCase.profile(token)
                if (response.errorMsg == null) {
                    _user.value = response.data
                    _tokenExpired.value = false
                } else {
                    if (response.errorMsg == "string") {
                        _tokenExpired.value = true
                    } else {
                        _userError.value = response.errorMsg
                    }
                }
            } catch (e: Exception) {
                _userError.value = e.message
            }
        }
    }
}}*/

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _userError = MutableStateFlow<String?>(null)
    val userError = _userError.asStateFlow()

    private val _tokenExpired = MutableStateFlow(false)
    val tokenExpired = _tokenExpired.asStateFlow()

    private fun markUserCheckComplete() {
        _isUserCheckComplete.value = true
    }
    private val _isUserCheckComplete = MutableStateFlow(false)
    val isUserCheckComplete = _isUserCheckComplete.asStateFlow()

    init {
        fetchUser()
    }

    fun fetchUser() {
        // Reset states
        _userError.value = null
        _tokenExpired.value = false
        _user.value = null
        _isUserCheckComplete.value = false // Set to false when starting the check

        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull() // Get token from injected manager
            if (token.isNullOrEmpty()) {
                markUserCheckComplete() // No token, so mark as complete and go to onboarding
                return@launch // Exit this coroutine
            }
            try {
                val retrofitResponse: Response<ProfileResponse> = authUseCase.profile(token)

                if (retrofitResponse.isSuccessful) {
                    val profileResponse: ProfileResponse? = retrofitResponse.body()
                    if (profileResponse != null) {
                        if (profileResponse.errorMsg == null) {
                            _user.value = profileResponse.data
                            _tokenExpired.value = false
                        } else {
                            if (profileResponse.errorMsg == "string") {
                                _tokenExpired.value = true
                            } else {
                                _userError.value = profileResponse.errorMsg
                            }
                        }
                    } else {
                        _userError.value = "Profile data not found in response body."
                    }
                } else {
                    val errorBodyString = retrofitResponse.errorBody()?.string()
                    val errorMessage = if (errorBodyString.isNullOrBlank()) {
                        "HTTP Error ${retrofitResponse.code()}: ${retrofitResponse.message()}"
                    } else {
                        "HTTP Error ${retrofitResponse.code()}: $errorBodyString"
                    }
                    _userError.value = errorMessage

                    if (retrofitResponse.code() == 401) {
                        _tokenExpired.value = true
                    }
                }
            } catch (e: Exception) {
                _userError.value = "Network or unexpected error: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                // IMPORTANT: Ensure this is set to true when the operation (success or failure) completes
                _isUserCheckComplete.value = true
            }
        }
    }
}