package com.ziko.data.remote.request

/**
 * ===============================================
 *  File: SignUpRequest.kt
 *  Layer: Data - Remote Requests
 *
 *  Description:
 *  Data model used to encapsulate all necessary fields required
 *  to register a new user on the Ziko platform.
 *
 *  This request body is used by the [com.ziko.data.remote.ApiService.signup] endpoint.
 *  It includes personal details as well as the authentication method.
 * ===============================================
 *
 * @property firstName The user's first name
 * @property lastName The user's last name
 * @property email The user's email address for account identification
 * @property password The password the user will use to log in
 * @property authMethod Indicates how the user is signing up (e.g., "email", "google")
 *
 * @see com.ziko.data.remote.ApiService.signup
 */
data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val authMethod: String
)
