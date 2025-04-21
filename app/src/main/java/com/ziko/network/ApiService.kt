package com.ziko.network

import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")  // Endpoint for login
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("signup") // Endpoint for signup
    suspend fun signup(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>
}
