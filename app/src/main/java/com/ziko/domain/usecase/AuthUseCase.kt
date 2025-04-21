package com.ziko.domain.usecase

import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.domain.repository.AuthRepository
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource

class AuthUseCase(private val authRepository: AuthRepository) {

    // Function to call the repository for login
    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return authRepository.login(email, password)
    }

    // Function to call the repository for sign-up
    suspend fun signup (signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        return authRepository.signup(signUpRequest)
    }
}
