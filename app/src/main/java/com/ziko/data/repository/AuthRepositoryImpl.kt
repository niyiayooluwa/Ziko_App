package com.ziko.data.repository

import android.util.Log
import com.ziko.data.model.AssessmentStatsItem
import com.ziko.data.remote.ChangePasswordRequest
import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.ProfileResponse
import com.ziko.data.remote.ScoreUpdateRequest
import com.ziko.data.remote.SignUpResponse
import com.ziko.data.remote.UpdateUserNameRequest
import com.ziko.domain.repository.AuthRepository
import com.ziko.network.ApiService
import com.ziko.network.LoginRequest
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource
import retrofit2.Response

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun signup(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        return try {
            // Call the API using the SignUpRequest object
            val response = apiService.signup(signUpRequest)
            // Assuming the response is successful, return the data
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            // Handle the exception if something goes wrong during the API call
            Resource.Error(e.message ?: "Unknown Error during sign-up")
        }
    }

    override suspend fun profile(token: String): Response<ProfileResponse> {
        return try {
            Log.d("AuthRepo", "Calling profile API with token: Bearer $token")
            val response = apiService.profile("Bearer $token")
            Log.d("AuthRepo", "Profile response body: ${response.body()?.toString()}")
            Log.d("AuthRepo", "Profile API response: ${response.code()} - ${response.message()}")
            response
        } catch (e: Exception) {
            Log.e("AuthRepo", "Profile API call failed: ${e.localizedMessage}", e)
            throw e
        }
    }


    override suspend fun getAssessmentStats(token: String): Result<List<AssessmentStatsItem>> {
        return try {
            val response = apiService.getAssessmentStats("Bearer $token")
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateHighestScore(token: String, lesson: String, score: Int): Result<Unit> {
        return try {
            val response = apiService.updateHighestScore("Bearer $token", lesson, ScoreUpdateRequest(score))
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("AssessmentUpdate", "Error response: $errorBody")
                return Result.failure(Exception("Score update failed: ${response.code()}"))
            }
            else Result.failure(Exception("Score update failed: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserName(token: String, firstName: String, lastName: String): Result<Unit> {
        return try {
            val response = apiService.updateUserName("Bearer $token", UpdateUserNameRequest(firstName, lastName))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Update Name Failed: ${response.code()} - $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(token: String): Result<Unit> {
        return try {
            val response = apiService.deleteAccount("Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Account Deletion Failed: ${response.code()} - $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(token: String, oldPassword: String, newPassword: String): Result<Unit> {
        return try {
            val request = ChangePasswordRequest(oldPassword, newPassword)
            val response = apiService.changePassword("Bearer $token", request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Password Change Failed: ${response.code()} - $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}