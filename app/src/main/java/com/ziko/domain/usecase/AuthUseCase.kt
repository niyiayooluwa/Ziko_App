package com.ziko.domain.usecase

import com.ziko.data.remote.model.AssessmentStatsItem
import com.ziko.data.remote.response.LoginResponse
import com.ziko.data.remote.response.ProfileResponse
import com.ziko.data.remote.response.SignUpResponse
import com.ziko.domain.repository.AuthRepository
import com.ziko.data.remote.request.SignUpRequest
import com.ziko.core.common.Resource
import retrofit2.Response

/**
 * Use case layer that coordinates authentication-related operations
 * between the domain layer and the repository implementation.
 *
 * Acts as a service layer to encapsulate business logic and make the
 * domain logic easier to manage, test, and mock.
 *
 * @param authRepository Repository instance used to execute operations.
 */
class AuthUseCase(private val authRepository: AuthRepository) {

    /**
     * Executes the login operation.
     *
     * @param email User's email.
     * @param password User's password.
     * @return A [Resource] containing the [LoginResponse] or an error.
     */
    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return authRepository.login(email, password)
    }

    /**
     * Executes the signup operation using a [SignUpRequest].
     *
     * @param signUpRequest Payload containing registration details.
     * @return A [Resource] containing the [SignUpResponse] or an error.
     */
    suspend fun signup(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        return authRepository.signup(signUpRequest)
    }

    /**
     * Fetches the user profile using the authentication token.
     *
     * @param token Bearer token for API authorization.
     * @return A [Response] containing the [ProfileResponse].
     */
    suspend fun profile(token: String): Response<ProfileResponse> {
        return authRepository.profile(token)
    }

    /**
     * Retrieves the user's assessment statistics.
     *
     * @param token Bearer token for authorization.
     * @return A [Result] containing a list of [AssessmentStatsItem] or an exception.
     */
    suspend fun getAssessmentStats(token: String): Result<List<AssessmentStatsItem>> {
        return authRepository.getAssessmentStats(token)
    }

    /**
     * Updates the highest score for a given lesson.
     *
     * @param token Bearer token for authorization.
     * @param lesson Lesson identifier (e.g., "lesson1").
     * @param score New highest score to set.
     * @return A [Result] indicating success or failure.
     */
    suspend fun updateHighestScore(token: String, lesson: String, score: Int): Result<Unit> {
        return authRepository.updateHighestScore(token, lesson, score)
    }

    /**
     * Updates the user's first and last names.
     *
     * @param token Bearer token for authorization.
     * @param firstName New first name.
     * @param lastName New last name.
     * @return A [Result] indicating success or failure.
     */
    suspend fun updateUserName(token: String, firstName: String, lastName: String): Result<Unit> {
        return authRepository.updateUserName(token, firstName, lastName)
    }

    /**
     * Deletes the current user's account.
     *
     * @param token Bearer token for authorization.
     * @return A [Result] indicating success or failure.
     */
    suspend fun deleteAccount(token: String): Result<Unit> {
        return authRepository.deleteAccount(token)
    }

    /**
     * Changes the user's password.
     *
     * @param token Bearer token for authorization.
     * @param oldPassword Current password.
     * @param newPassword New password to set.
     * @return A [Result] indicating success or failure.
     */
    suspend fun changePassword(token: String, oldPassword: String, newPassword: String): Result<Unit> {
        return authRepository.changePassword(token, oldPassword, newPassword)
    }
}
