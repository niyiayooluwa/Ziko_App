package com.ziko.data.remote

data class ProfileResponse(
    val msg: String,
    val errorMsg: String?,
    val data: UserData
)

data class UserData (
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String,
)