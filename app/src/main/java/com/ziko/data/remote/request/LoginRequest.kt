package com.ziko.data.remote.request

/**
 * ===============================================
 *  File: LoginRequest.kt
 *  Layer: Data - Remote Requests
 *
 *  Description:
 *  Data class representing the request body sent to the login endpoint
 *  of the Ziko API. It encapsulates the user's login credentials.
 *
 *  This model is used by Retrofit in the [com.ziko.data.remote.ApiService.login] call.
 * ===============================================
 *
 * @property email User's email address used for login
 * @property password User's raw password for authentication
 *
 * @see com.ziko.data.remote.ApiService.login
 */
data class LoginRequest(
    val email: String,
    val password: String
)
