package com.ziko.data.remote.response

/**
 * File: UserUpdateResponses.kt
 * Layer: data → remote → response
 *
 * Contains response models for various user-related API actions such as updating username,
 * changing password, and deleting account. These responses follow a consistent structure
 * provided by the backend for success and error feedback.
 *
 * Each response typically contains:
 * - `msg`: A message indicating the result of the operation (e.g., "Update successful").
 * - `errorMsg`: A message returned when the operation fails (e.g., "Password too short").
 * - `data`: Additional data returned by the server. In most cases for these actions, it's a status string.
 *
 * These models are useful for displaying operation status to the user after API interactions.
 */

/**
 * Response from the server after updating the user's name.
 *
 * @property msg A message describing the result (e.g., "Name updated successfully").
 * @property errorMsg An error message in case the update fails.
 * @property data Optional string data returned from the backend, may be null.
 */
data class UpdateUserNameResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)

/**
 * Response from the server after a change password request.
 *
 * @property msg A message indicating success (e.g., "Password changed").
 * @property errorMsg Error message if the password change failed.
 * @property data Optional field, may contain a status string or null.
 */
data class ChangePasswordResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)

/**
 * Response from the server after a user deletion request.
 *
 * @property msg A success message if the deletion was processed (e.g., "Account deleted").
 * @property errorMsg An error message if the deletion fails.
 * @property data May contain confirmation or tracking info; nullable.
 */
data class DeleteAccountResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)
