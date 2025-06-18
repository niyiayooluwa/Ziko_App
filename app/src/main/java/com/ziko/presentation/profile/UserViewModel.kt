package com.ziko.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.remote.response.UserData
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.core.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel responsible for managing user-related state and actions such as authentication,
 * profile updates, logout, and password changes.
 *
 * Uses `AuthUseCase` for interacting with remote API and `DataStoreManager` for local persistence.
 *
 * @property authUseCase Use case class that contains authentication-related operations.
 * @property dataStoreManager Manages access to locally persisted data such as token, user info, and settings.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    // --- StateFlows exposed to UI ---

    /** Currently authenticated user, or null if not fetched yet */
    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    /** Error message from last user-related operation (usually profile fetch) */
    private val _userError = MutableStateFlow<String?>(null)
    val userError = _userError.asStateFlow()

    /** Profile picture URI persisted in local storage */
    private val _profilePicUri = MutableStateFlow<String?>(null)
    val profilePicUri: StateFlow<String?> = _profilePicUri

    /** Flag indicating if the token used for API calls has expired */
    private val _tokenExpired = MutableStateFlow(false)
    val tokenExpired = _tokenExpired.asStateFlow()

    /** Flag indicating whether the initial user check (e.g., on app startup) is complete */
    private val _isUserCheckComplete = MutableStateFlow(false)
    val isUserCheckComplete = _isUserCheckComplete.asStateFlow()

    // --- Async operation results ---

    /** Result of attempting to update the user's first and last name */
    private val _userUpdateResult = MutableStateFlow<Result<Unit>?>(null)
    val userUpdateResult = _userUpdateResult.asStateFlow()

    /** Result of attempting to delete the user's account */
    private val _accountDeleteResult = MutableStateFlow<Result<Unit>?>(null)
    val accountDeleteResult = _accountDeleteResult.asStateFlow()

    /** Result of attempting to change the user's password */
    private val _passwordChangeResult = MutableStateFlow<Result<Unit>?>(null)
    val passwordChangeResult = _passwordChangeResult.asStateFlow()

    // --- Internal helper to mark user check complete ---
    private fun markUserCheckComplete() {
        _isUserCheckComplete.value = true
    }

    init {
        // On ViewModel creation, try to load user if token exists
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            _profilePicUri.value = dataStoreManager.getProfilePicUri()

            if (!token.isNullOrEmpty()) {
                fetchUser(forceRefresh = false)
            } else {
                markUserCheckComplete()
            }
        }
    }

    /**
     * Fetches the current user's profile from the remote API.
     *
     * If `forceRefresh` is false, will return cached data if available.
     *
     * @param forceRefresh If true, ignores local cache and fetches from server.
     * @param token Optional manual override for the auth token.
     */
    fun fetchUser(forceRefresh: Boolean = false, token: String? = null) {
        viewModelScope.launch {
            _userError.value = null
            _tokenExpired.value = false

            // Use in-memory data if not forcing refresh
            if (!forceRefresh && _user.value != null) {
                markUserCheckComplete()
                return@launch
            }

            // Use cached local data if not forcing refresh
            val cachedUser = dataStoreManager.getCachedUser.firstOrNull()
            if (!forceRefresh && cachedUser != null) {
                _user.value = cachedUser
                markUserCheckComplete()
                return@launch
            }

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
                    dataStoreManager.saveUser(userData)
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

                    // Handle expired token
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

    /**
     * Updates the profile picture URI in local storage and emits new value.
     *
     * @param uri The local URI of the new profile image.
     */
    fun updateProfilePic(uri: String) {
        viewModelScope.launch {
            dataStoreManager.saveProfilePicUri(uri)
            _profilePicUri.value = uri
        }
    }

    /**
     * Logs the user out by clearing local data and resetting ViewModel state.
     *
     * @param onLogoutComplete Callback to trigger navigation or clean-up after logout.
     */
    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                dataStoreManager.clearAssessmentStats()
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

    /**
     * Updates the user's first and last name using the provided values.
     *
     * Emits result to [userUpdateResult].
     *
     * @param firstName New first name.
     * @param lastName New last name.
     */
    fun updateUserName(firstName: String, lastName: String) {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            if (token.isNullOrEmpty()) {
                _userUpdateResult.value = Result.failure(Exception("Token not found"))
                return@launch
            }

            val result = authUseCase.updateUserName(token, firstName, lastName)
            _userUpdateResult.value = result

            if (result.isSuccess) {
                fetchUser(forceRefresh = true)
            }
        }
    }

    /**
     * Deletes the user account from the server.
     *
     * If successful, clears all local user data and triggers [onDeleted] callback.
     *
     * @param onDeleted Callback to handle post-deletion logic (e.g., navigate to splash/login).
     */
    fun deleteUserAccount(onDeleted: () -> Unit) {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            if (token.isNullOrEmpty()) {
                _accountDeleteResult.value = Result.failure(Exception("Token not found"))
                return@launch
            }

            val result = authUseCase.deleteAccount(token)
            _accountDeleteResult.value = result

            if (result.isSuccess) {
                logout(onDeleted)
            }
        }
    }

    /**
     * Attempts to change the user's password using the provided old and new password.
     *
     * Emits result to [passwordChangeResult].
     *
     * @param oldPassword The user's current password.
     * @param newPassword The new password to be set.
     */
    fun changeUserPassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            if (token.isNullOrEmpty()) {
                _passwordChangeResult.value = Result.failure(Exception("Token not found"))
                return@launch
            }

            val result = authUseCase.changePassword(token, oldPassword, newPassword)
            _passwordChangeResult.value = result
        }
    }
}

