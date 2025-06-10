package com.ziko.data.remote

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)