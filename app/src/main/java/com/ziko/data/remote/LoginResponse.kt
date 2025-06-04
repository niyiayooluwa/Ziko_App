package com.ziko.data.remote

data class LoginResponse(
    val msg: String,
    val errorMsg: String,
    val data: String, //token
)
