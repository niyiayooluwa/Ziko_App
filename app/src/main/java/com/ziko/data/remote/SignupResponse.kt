package com.ziko.data.remote

data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null,
    val token: String? = null
)