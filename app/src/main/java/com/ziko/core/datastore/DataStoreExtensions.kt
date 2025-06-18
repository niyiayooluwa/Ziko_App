package com.ziko.core.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * =====================================================
 *  File: DataStoreExtensions.kt
 *  Layer: Core - Utilities
 *
 *  Description:
 *  Kotlin extension property on [Context] to provide a singleton instance
 *  of Jetpack DataStore with a predefined name "auth_prefs".
 *
 *  This instance is used to persist authentication-related key-value pairs
 *  like tokens, onboarding flags, and user session data in a structured and
 *  asynchronous way.
 *
 *  Usage:
 *  ```
 *  context.dataStore.edit { prefs ->
 *      prefs[stringPreferencesKey("auth_token")] = "xyz"
 *  }
 *  ```
 *
 *  @see androidx.datastore.preferences.preferencesDataStore
 *  @see androidx.datastore.core.DataStore
 * =====================================================
 */

// Extension property on Context that initializes a Preferences DataStore named "auth_prefs"
// This ensures the same instance is used app-wide for consistent preference storage.
val Context.dataStore by preferencesDataStore(name = "auth_prefs")
