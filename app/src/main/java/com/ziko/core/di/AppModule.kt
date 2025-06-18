package com.ziko.core.di

import com.ziko.data.repository.AuthRepositoryImpl
import com.ziko.domain.repository.AuthRepository
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * ===============================================
 *  File: AppModule.kt
 *  Layer: Dependency Injection
 *  Framework: Dagger Hilt
 *
 *  Description:
 *  AppModule is a Dagger Hilt module that provides singleton-scoped dependencies
 *  for networking and authentication logic in the Ziko app.
 *  It binds:
 *   - Retrofit instance configured for the API base URL.
 *   - ApiService interface for network communication.
 *   - AuthRepository implementation for domain-level abstraction.
 *   - AuthUseCase for encapsulating login logic at the use case level.
 *
 *  This module is installed in the SingletonComponent, meaning all dependencies
 *  provided here will live as long as the application.
 * ===============================================
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton Retrofit instance configured with the base API URL
     * and Gson converter for JSON serialization/deserialization.
     *
     * @return [Retrofit] instance to be used for network communication
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-ziko.onrender.com/") // Base URL for Ziko API
            .addConverterFactory(GsonConverterFactory.create()) // Gson for JSON parsing
            .build()
    }

    /**
     * Provides a singleton ApiService implementation created via Retrofit.
     *
     * @param retrofit Retrofit instance to create the ApiService
     * @return [ApiService] instance used for making API calls
     *
     * @see provideRetrofit
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides a singleton AuthRepository implementation which serves as
     * the data source for authentication-related operations.
     *
     * @param apiService Instance used to perform network calls related to authentication
     * @return [AuthRepository] implementation that can be injected into use cases or view models
     *
     * @see AuthRepositoryImpl
     */
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    /**
     * Provides a singleton instance of [AuthUseCase], which contains the domain
     * logic for user authentication.
     *
     * This use case is responsible for encapsulating business logic around
     * login functionality, and decouples the ViewModel from data layer.
     *
     * @param repository The AuthRepository implementation to delegate authentication logic
     * @return [AuthUseCase] instance to be injected into ViewModels or Presentation layer
     *
     * @see provideAuthRepository
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): AuthUseCase {
        return AuthUseCase(repository)
    }
}

