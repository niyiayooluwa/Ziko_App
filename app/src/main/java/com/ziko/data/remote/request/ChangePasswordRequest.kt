package com.ziko.data.remote.request

/**
 * File: ChangePasswordRequest.kt
 * Layer: data → remote → request
 *
 * Represents the payload for a password change request sent to the remote server.
 *
 * This data class is used in network operations where the user needs to update their password.
 * It contains both the old and new password fields required for authentication and validation.
 *
 * Typically used in secure authenticated flows where the backend verifies the old password
 * before updating to the new one.
 *
 * Example Usage:
 * ```
 * val request = ChangePasswordRequest(
 *     oldPassword = "oldSecret123",
 *     newPassword = "newSecret456"
 * )
 * api.changePassword(request)
 * ```
 */
data class ChangePasswordRequest(
    val oldPassword: String, // The user's current password to validate identity
    val newPassword: String  // The new password the user wants to set
)
