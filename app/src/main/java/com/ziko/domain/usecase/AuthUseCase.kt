package com.ziko.domain.usecase

import com.ziko.data.model.AssessmentStatsItem
import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.ProfileResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.domain.repository.AuthRepository
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource
import retrofit2.Response

class AuthUseCase(private val authRepository: AuthRepository) {

    // Function to call the repository for login
    suspend fun login (email: String, password: String): Resource<LoginResponse> {
        return authRepository.login(email, password)
    }

    // Function to call the repository for sign-up
    suspend fun signup (signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        return authRepository.signup(signUpRequest)
    }

    suspend fun profile (token: String): Response<ProfileResponse> {
        return authRepository.profile(token)
    }

    suspend fun getAssessmentStats(token: String): Result<List<AssessmentStatsItem>> {
        return authRepository.getAssessmentStats(token)
    }

    suspend fun updateHighestScore(token: String, lesson: String, score: Int): Result<Unit> {
        return authRepository.updateHighestScore(token, lesson, score)
    }
}
