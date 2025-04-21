package com.ziko.data.repository

import com.ziko.data.remote.LoginResponse
import com.ziko.data.remote.SignUpResponse
import com.ziko.domain.repository.AuthRepository
import com.ziko.network.ApiService
import com.ziko.network.LoginRequest
import com.ziko.network.SignUpRequest
import com.ziko.util.Resource

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
}