package com.ziko.data.remote.response

/**
 * File: LoginResponse.kt
 * Layer: data → remote → response
 *
 * Represents the server response after a successful or failed login attempt.
 *
 * This response includes a status message (`msg`), an error message if applicable (`errorMsg`),
 * and the authentication token (`data`) which is typically a JWT used for subsequent authorized requests.
 *
 * This class is typically deserialized from JSON returned by a login API endpoint.
 *
 * @property msg A human-readable message indicating login success or failure.
 * @property errorMsg Contains the server's error message if the login failed (empty/null if successful).
 * @property data JWT token returned upon successful authentication. Used for future authenticated requests.
 *
 * Example JSON response:
 * ```
 * {
 *   "msg": "Login successful",
 *   "errorMsg": "",
 *   "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
 * }
 * ```
 *
 */
data class LoginResponse(
    val msg: String,         // Success or informational message from server
    val errorMsg: String,    // Optional error message if login failed
    val data: String         // JWT token string returned upon successful login
)
