package com.ziko.data.remote

data class ProfileResponse(
    val msg: String,
    val errorMsg: String?,
    val data: UserData
)

data class UserData (
    val first_name: String,
    val last_name: String,
    val email: String,
    val created_at: String,
)