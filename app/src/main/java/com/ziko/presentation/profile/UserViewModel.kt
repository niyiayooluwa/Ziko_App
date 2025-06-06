package com.ziko.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.remote.UserData
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    private val _isUserCheckComplete = MutableStateFlow(false)
    val isUserCheckComplete = _isUserCheckComplete.asStateFlow()

    private fun markUserCheckComplete() {
        _isUserCheckComplete.value = true
    }

    init {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            if (!token.isNullOrEmpty()) {
                fetchUser(forceRefresh = false)
            } else {
                markUserCheckComplete()
            }
        }
    }

    fun fetchUser(forceRefresh: Boolean = false, token: String? = null) {
        viewModelScope.launch {
            _userError.value = null
            _tokenExpired.value = false

            if (!forceRefresh && _user.value != null) {
                markUserCheckComplete()
                return@launch
            }

            // Check local cache
            val cachedUser = dataStoreManager.getCachedUser.firstOrNull()
            if (!forceRefresh && cachedUser != null) {
                _user.value = cachedUser
                markUserCheckComplete()
                return@launch
            }

            // Use passed token if available; fallback to data store
            val authToken = token ?: dataStoreManager.getToken.firstOrNull()
            if (authToken.isNullOrEmpty()) {
                markUserCheckComplete()
                return@launch
            }

            try {
                val response = authUseCase.profile(authToken)
                if (response.isSuccessful && response.body()?.data != null) {
                    val userData = response.body()!!.data
                    _user.value = userData
                    dataStoreManager.saveUser(userData) // Save to local cache
                    _tokenExpired.value = false
                } else {
                    val errorBodyString = response.errorBody()?.string()
                    val errorMessage = if (errorBodyString.isNullOrBlank()) {
                        "HTTP Error ${response.code()}: ${response.message()}"
                    } else {
                        "HTTP Error ${response.code()}: $errorBodyString"
                    }
                    Log.e("UserViewModel", errorMessage)
                    _userError.value = errorMessage

                    if (response.code() == 401) {
                        _tokenExpired.value = true
                    }
                }
            } catch (e: Exception) {
                _userError.value = "Network error: ${e.localizedMessage ?: "Unknown"}"
            } finally {
                markUserCheckComplete()
            }
        }
    }


    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                dataStoreManager.clearToken()
                dataStoreManager.clearUser()
                _user.value = null
                _tokenExpired.value = false
                _userError.value = null
                _isUserCheckComplete.value = false
                onLogoutComplete()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Logout failed: ${e.message}", e)
            }
        }
    }
}