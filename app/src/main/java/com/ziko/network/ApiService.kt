package com.ziko.network

import com.ziko.data.remote.AssessmentStatsResponse
import com.ziko.data.remote.ChangePasswordRequest
import com.ziko.data.remote.ChangePasswordResponse
import com.ziko.data.remote.DeleteAccountResponse
import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.ProfileResponse
import com.ziko.data.remote.ScoreUpdateRequest
import com.ziko.data.remote.ScoreUpdateResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.data.remote.UpdateUserNameRequest
import com.ziko.data.remote.UpdateUserNameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")  // Endpoint for login
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("signup") // Endpoint for signup
    suspend fun signup(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>

    @GET("profile") //Endpoint to get user info
    suspend fun profile(
        @Header ("Authorization") token: String
    ): Response<ProfileResponse>

    @GET("assessments")
    suspend fun getAssessmentStats(
        @Header("Authorization") token: String
    ): AssessmentStatsResponse

    @POST("assessments/{lesson}/score")
    suspend fun updateHighestScore(
        @Header("Authorization") token: String,
        @Path("lesson") lesson: String,
        @Body score : ScoreUpdateRequest
    ): Response<ScoreUpdateResponse>

    @PUT("profile")
    suspend fun updateUserName(
        @Header("Authorization") token: String,
        @Body body: UpdateUserNameRequest
    ): Response<UpdateUserNameResponse>

    @DELETE("profile")
    suspend fun deleteAccount(
        @Header("Authorization") token: String
    ): Response<DeleteAccountResponse>

    @POST("profile/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body body: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}
