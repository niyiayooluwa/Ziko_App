package com.ziko.core.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * ========================================================
 *  File: ConnectivityModule.kt
 *  Layer: Core - Dependency Injection
 *
 *  Description:
 *  Provides a singleton instance of [ConnectivityManager] via Hilt's
 *  DI framework. This enables any component in the app to easily
 *  inject access to the device’s network state and connectivity information.
 *
 *  This module is installed into the [SingletonComponent], making
 *  it available throughout the entire application lifecycle.
 *
 *  Usage:
 *  ```
 *  @Inject lateinit var connectivityManager: ConnectivityManager
 *  ```
 *
 *  @see android.net.ConnectivityManager
 *  @see dagger.hilt.components.SingletonComponent
 * ========================================================
 */
@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    /**
     * Provides a singleton instance of [ConnectivityManager] by accessing the
     * system service from the [ApplicationContext].
     *
     * @param context Application context provided by Hilt
     * @return The system [ConnectivityManager] used for checking network status
     */
    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
