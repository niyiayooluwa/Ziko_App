package com.ziko.domain.repository

import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>

    suspend fun signup(signUpRequest: SignUpRequest): Resource<SignUpResponse>
}
