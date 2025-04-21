package com.ziko.data.remote

data class LoginResponse(
    val token: String,
    val userId: String,
    val name: String,
    val message: String,
    val success: Boolean
)
