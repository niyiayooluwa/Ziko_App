package com.ziko.data.remote.response

/**
 * File: ProfileResponse.kt
 * Layer: data → remote → response
 *
 * Represents the response returned by the server when fetching user profile information.
 * This data class includes both metadata about the request's success and the actual user details
 * in a nested [UserData] object.
 *
 * @property msg A general status message from the server (e.g., "Profile fetched successfully").
 * @property errorMsg Contains the error message in case of failure (nullable).
 * @property data A [UserData] object holding user-specific details.
 *
 * Example JSON structure:
 * ```
 * {
 *   "msg": "Success",
 *   "errorMsg": null,
 *   "data": {
 *     "first_name": "Jane",
 *     "last_name": "Doe",
 *     "email": "jane@example.com",
 *     "created_at": "2024-01-01T10:00:00Z"
 *   }
 * }
 * ```
 */
data class ProfileResponse(
    val msg: String,
    val errorMsg: String?,
    val data: UserData
)

/**
 * Contains basic user profile information returned from the remote API.
 *
 * This is typically nested within a [ProfileResponse] and used to populate profile screens or
 * store cached user info locally.
 *
 * @property first_name The user's first name.
 * @property last_name The user's last name.
 * @property email The user's registered email address.
 * @property created_at String representing the account creation timestamp.
 */
data class UserData(
    val first_name: String,
    val last_name: String,
    val email: String,
    val created_at: String,
)
