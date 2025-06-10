package com.ziko.data.remote

data class UpdateUserNameResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)

data class ChangePasswordResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)

data class DeleteAccountResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: String?
)
