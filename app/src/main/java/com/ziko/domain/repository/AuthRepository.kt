package com.ziko.domain.repository

import com.ziko.data.model.AssessmentStatsItem
import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.ProfileResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource
import retrofit2.Response

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>

    suspend fun signup(signUpRequest: SignUpRequest): Resource<SignUpResponse>

    suspend fun profile(token: String): Response<ProfileResponse>

    suspend fun getAssessmentStats(token: String): Result<List<AssessmentStatsItem>>

    suspend fun updateHighestScore(token: String, lesson: String, score: Int): Result<Unit>

    suspend fun updateUserName(token: String, firstName: String, lastName: String): Result<Unit>

    suspend fun deleteAccount(token: String): Result<Unit>

    suspend fun changePassword(token: String, oldPassword: String, newPassword: String): Result<Unit>
}
