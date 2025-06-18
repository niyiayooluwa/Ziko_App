package com.ziko.data.remote.request

/**
 * File: UpdateUserNameRequest.kt
 * Layer: data → remote → request
 *
 * Data Transfer Object (DTO) representing the payload sent to the server to update a user's name.
 * This is typically used in profile edit flows where the user updates their identity information.
 *
 * @property firstName New first name to be associated with the user account.
 * @property lastName New last name to be associated with the user account.
 *
 * Example JSON payload:
 * ```
 * {
 *   "firstName": "John",
 *   "lastName": "Doe"
 * }
 * ```
 */
data class UpdateUserNameRequest(
    val firstName: String,
    val lastName: String
)
