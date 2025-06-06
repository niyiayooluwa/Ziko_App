package com.ziko.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.ziko.data.remote.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val dataStore = context.dataStore  // uses the top-level singleton

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    val getToken: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    suspend fun clearToken() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    suspend fun saveUser(user: UserData) {
        val userJson = Gson().toJson(user)
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey("user_data")] = userJson
        }
    }

    val getCachedUser: Flow<UserData?> = context.dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("user_data")]?.let { json ->
            Gson().fromJson(json, UserData::class.java)
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.remove(stringPreferencesKey("user_data")) }
    }
}
