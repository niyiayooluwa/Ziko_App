package com.ziko.data.remote.response

/**
 * File: SignUpResponse.kt
 * Layer: data → remote → response
 *
 * Represents the server's response after a successful or failed user registration attempt.
 * Contains authentication token, user identity details, and success messaging.
 *
 * @property token JWT token provided upon successful registration. Used for authenticating future requests.
 * @property userId Unique identifier assigned to the new user by the server.
 * @property name Full name of the newly registered user (typically a combination of first and last name).
 * @property message A human-readable status message from the server (e.g., "Account created successfully").
 * @property success Boolean flag indicating whether the sign-up process was successful (`true`) or failed (`false`).
 *
 * Example response:
 * ```
 * {
 *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
 *   "userId": "a23d7e9f",
 *   "name": "Jane Doe",
 *   "message": "Account created successfully",
 *   "success": true
 * }
 * ```
 *
 */

data class SignUpResponse(
    val token: String,
    val userId: String,
    val name: String,
    val message: String,
    val success: Boolean
)
