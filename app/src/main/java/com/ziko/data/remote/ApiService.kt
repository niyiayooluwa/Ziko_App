package com.ziko.data.remote

import com.ziko.data.remote.request.ChangePasswordRequest
import com.ziko.data.remote.request.LoginRequest
import com.ziko.data.remote.request.ScoreUpdateRequest
import com.ziko.data.remote.request.SignUpRequest
import com.ziko.data.remote.request.UpdateUserNameRequest
import com.ziko.data.remote.response.AssessmentStatsResponse
import com.ziko.data.remote.response.ChangePasswordResponse
import com.ziko.data.remote.response.DeleteAccountResponse
import com.ziko.data.remote.response.LoginResponse
import com.ziko.data.remote.response.ProfileResponse
import com.ziko.data.remote.response.ScoreUpdateResponse
import com.ziko.data.remote.response.SignUpResponse
import com.ziko.data.remote.response.UpdateUserNameResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * ===============================================
 *  File: ApiService.kt
 *  Layer: Data - Network
 *
 *  Description:
 *  ApiService defines the Retrofit-based network interface for communicating
 *  with the Ziko backend API. It includes endpoints for:
 *   - Authentication (Login, Signup)
 *   - User profile management (fetch, update, delete, change password)
 *   - Assessment-related operations (stats, score updates)
 *
 *  All functions are marked `suspend` for coroutine support and perform
 *  their tasks asynchronously.
 * ===============================================
 */
interface ApiService {

    /**
     * Authenticates the user and returns a login response containing user info and token.
     *
     * @param request Request body containing email and password credentials
     * @return [Response] wrapping [LoginResponse] containing user data and JWT token
     *
     * @throws retrofit2.HttpException on unsuccessful response
     * @see LoginResponse
     */
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    /**
     * Registers a new user with the provided signup credentials.
     *
     * @param request Request body with new user registration details
     * @return [Response] wrapping [SignUpResponse] with server confirmation or error
     *
     * @see SignUpResponse
     */
    @POST("signup")
    suspend fun signup(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>

    /**
     * Retrieves the current authenticated user's profile.
     *
     * @param token JWT token to authorize the request
     * @return [Response] wrapping [ProfileResponse] with user profile data
     *
     * @throws retrofit2.HttpException on authorization failure
     * @see ProfileResponse
     */
    @GET("profile")
    suspend fun profile(
        @Header("Authorization") token: String
    ): Response<ProfileResponse>

    /**
     * Fetches the user's assessment statistics.
     *
     * @param token JWT token for authentication
     * @return [AssessmentStatsResponse] containing detailed stats on assessments
     */
    @GET("assessments")
    suspend fun getAssessmentStats(
        @Header("Authorization") token: String
    ): AssessmentStatsResponse

    /**
     * Submits the highest score for a given lesson.
     *
     * @param token JWT token for authentication
     * @param lesson Lesson identifier whose score is being updated
     * @param score [ScoreUpdateRequest] containing new high score
     * @return [Response] wrapping [ScoreUpdateResponse] indicating success/failure
     *
     * @see ScoreUpdateRequest
     */
    @POST("assessments/{lesson}/score")
    suspend fun updateHighestScore(
        @Header("Authorization") token: String,
        @Path("lesson") lesson: String,
        @Body score: ScoreUpdateRequest
    ): Response<ScoreUpdateResponse>

    /**
     * Updates the authenticated user's username.
     *
     * @param token JWT token for authentication
     * @param body [UpdateUserNameRequest] containing the new username
     * @return [Response] wrapping [UpdateUserNameResponse] with status message
     */
    @PUT("profile")
    suspend fun updateUserName(
        @Header("Authorization") token: String,
        @Body body: UpdateUserNameRequest
    ): Response<UpdateUserNameResponse>

    /**
     * Deletes the authenticated user's account.
     *
     * @param token JWT token for authentication
     * @return [Response] wrapping [DeleteAccountResponse] with deletion status
     *
     * @throws retrofit2.HttpException if unauthorized or operation fails
     */
    @DELETE("profile")
    suspend fun deleteAccount(
        @Header("Authorization") token: String
    ): Response<DeleteAccountResponse>

    /**
     * Changes the user's password after verifying their identity.
     *
     * @param token JWT token for authentication
     * @param body [ChangePasswordRequest] containing current and new passwords
     * @return [Response] wrapping [ChangePasswordResponse] with status info
     *
     * @throws retrofit2.HttpException if validation fails
     */
    @POST("profile/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body body: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}
