package com.ziko.domain.repository

import com.ziko.data.remote.model.AssessmentStatsItem
import com.ziko.data.remote.response.LoginResponse
import com.ziko.data.remote.response.ProfileResponse
import com.ziko.data.remote.response.SignUpResponse
import com.ziko.data.remote.request.SignUpRequest
import com.ziko.core.common.Resource
import retrofit2.Response

/**
 * Repository interface that defines the contract for authentication-related operations.
 *
 * This abstraction allows the domain layer to remain independent of the data source
 * (e.g., remote API or local DB) and enables easier testing and separation of concerns.
 *
 * All operations are asynchronous and suspendable, designed to be used with coroutines.
 */
interface AuthRepository {

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [Resource] wrapping [LoginResponse] on success or an error message.
     */
    suspend fun login(email: String, password: String): Resource<LoginResponse>

    /**
     * Registers a new user using the provided [SignUpRequest] payload.
     *
     * @param signUpRequest Request body containing user registration details.
     * @return [Resource] wrapping [SignUpResponse] or an error message.
     */
    suspend fun signup(signUpRequest: SignUpRequest): Resource<SignUpResponse>

    /**
     * Retrieves the profile data of the currently authenticated user.
     *
     * @param token Bearer token for authorization.
     * @return [Response] containing the [ProfileResponse] payload.
     */
    suspend fun profile(token: String): Response<ProfileResponse>

    /**
     * Fetches assessment statistics for the user across all lessons.
     *
     * @param token Bearer token for authorization.
     * @return [Result] containing a list of [AssessmentStatsItem] or an exception on failure.
     */
    suspend fun getAssessmentStats(token: String): Result<List<AssessmentStatsItem>>

    /**
     * Updates the highest score for a specific lesson.
     *
     * @param token Bearer token for authorization.
     * @param lesson The lesson identifier (e.g., "lesson1").
     * @param score The new highest score to be updated.
     * @return [Result] indicating success or failure.
     */
    suspend fun updateHighestScore(token: String, lesson: String, score: Int): Result<Unit>

    /**
     * Updates the user's first and last name.
     *
     * @param token Bearer token for authorization.
     * @param firstName New first name.
     * @param lastName New last name.
     * @return [Result] indicating success or failure.
     */
    suspend fun updateUserName(token: String, firstName: String, lastName: String): Result<Unit>

    /**
     * Permanently deletes the user's account.
     *
     * @param token Bearer token for authorization.
     * @return [Result] indicating success or failure.
     */
    suspend fun deleteAccount(token: String): Result<Unit>

    /**
     * Changes the user's password.
     *
     * @param token Bearer token for authorization.
     * @param oldPassword The current password.
     * @param newPassword The new password to be set.
     * @return [Result] indicating success or failure.
     */
    suspend fun changePassword(token: String, oldPassword: String, newPassword: String): Result<Unit>
}
