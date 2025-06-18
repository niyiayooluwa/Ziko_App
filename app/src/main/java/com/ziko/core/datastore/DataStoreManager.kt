package com.ziko.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ziko.domain.model.AssessmentCardInfo
import com.ziko.data.remote.response.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ============================================================
 *  File: DataStoreManager.kt
 *  Layer: Core - Persistence
 *
 *  Description:
 *  Wrapper class for managing preference-based persistence
 *  using Jetpack DataStore. Provides centralized access for
 *  storing and retrieving:
 *    - Auth token
 *    - Profile picture URI
 *    - Serialized user data
 *    - Cached assessment statistics with timestamps
 *
 *  Implements best practices for state caching and JSON serialization.
 *
 *  Usage:
 *  ```
 *  val token = dataStoreManager.getToken.collectAsState()
 *  dataStoreManager.saveUser(user)
 *  ```
 *
 *  @see androidx.datastore.preferences.preferencesDataStore
 *  @see com.ziko.core.datastore.dataStore
 * ============================================================
 */
@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    // Keys for different stored preferences
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val PROFILE_PIC_URI = stringPreferencesKey("profile_pic_uri")
    private val ASSESSMENT_STATS_KEY = stringPreferencesKey("assessment_stats")
    private val ASSESSMENT_STATS_TIMESTAMP_KEY = longPreferencesKey("assessment_stats_timestamp")

    /**
     * Save authentication token to DataStore
     *
     * @param token The bearer token string
     */
    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    /**
     * Observe stored authentication token as a [Flow]
     *
     * @return Flow emitting the token string or null
     */
    val getToken: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    /**
     * Save URI string of user's profile picture
     *
     * @param uri The URI string of the profile image
     */
    suspend fun saveProfilePicUri(uri: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_PIC_URI] = uri
        }
    }

    /**
     * Retrieve the stored profile picture URI
     *
     * @return URI string or null
     */
    suspend fun getProfilePicUri(): String? {
        return dataStore.data.first()[PROFILE_PIC_URI]
    }

    /**
     * Remove saved authentication token from DataStore
     */
    suspend fun clearToken() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    /**
     * Save serialized user data into DataStore
     *
     * @param user User object to be saved
     */
    suspend fun saveUser(user: UserData) {
        val userJson = Gson().toJson(user)
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("user_data")] = userJson
        }
    }

    /**
     * Observe deserialized user data from DataStore
     *
     * @return Flow emitting a [UserData] object or null
     */
    val getCachedUser: Flow<UserData?> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("user_data")]?.let { json ->
            Gson().fromJson(json, UserData::class.java)
        }
    }

    /**
     * Clear serialized user object from DataStore
     */
    suspend fun clearUser() {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey("user_data"))
        }
    }

    /**
     * Save cached assessment statistics and timestamp
     *
     * @param stats List of [AssessmentCardInfo] representing assessment progress
     */
    suspend fun saveAssessmentStats(stats: List<AssessmentCardInfo>) {
        val statsJson = Gson().toJson(stats)
        val currentTime = System.currentTimeMillis()

        dataStore.edit { prefs ->
            prefs[ASSESSMENT_STATS_KEY] = statsJson
            prefs[ASSESSMENT_STATS_TIMESTAMP_KEY] = currentTime
        }
    }

    /**
     * Observe cached assessment stats from DataStore
     *
     * @return Flow emitting a list of [AssessmentCardInfo] or null
     */
    val getCachedAssessmentStats: Flow<List<AssessmentCardInfo>?> = dataStore.data.map { prefs ->
        prefs[ASSESSMENT_STATS_KEY]?.let { json ->
            try {
                val type = object : TypeToken<List<AssessmentCardInfo>>() {}.type
                Gson().fromJson<List<AssessmentCardInfo>>(json, type)
            } catch (e: Exception) {
                null
            }
        }
    }

    /**
     * Get timestamp of when assessment stats were last cached
     *
     * @return Unix timestamp in milliseconds or 0L if not set
     */
    suspend fun getAssessmentStatsTimestamp(): Long {
        return dataStore.data.first()[ASSESSMENT_STATS_TIMESTAMP_KEY] ?: 0L
    }

    /**
     * Clear both assessment stats and timestamp
     */
    suspend fun clearAssessmentStats() {
        dataStore.edit { prefs ->
            prefs.remove(ASSESSMENT_STATS_KEY)
            prefs.remove(ASSESSMENT_STATS_TIMESTAMP_KEY)
        }
    }

    /**
     * Get highest score of a lesson by its title
     *
     * @param lessonTitle The title of the lesson
     * @return The highest score if found, or null
     */
    suspend fun getLessonHighScoreWithTitle(lessonTitle: String): Int? {
        return getCachedAssessmentStats.first()?.find {
            it.title.equals(lessonTitle, ignoreCase = true)
        }?.highestScore
    }

    /**
     * Get highest score of a lesson by its ID
     *
     * @param lessonId The ID of the lesson
     * @return The highest score if found, or null
     */
    suspend fun getLessonHighScoreWithId(lessonId: String): Int? {
        return getCachedAssessmentStats.first()?.find {
            it.id.equals(lessonId, ignoreCase = true)
        }?.highestScore
    }

    /**
     * Check if cached assessment data is older than 5 minutes
     *
     * @return True if stale, false otherwise
     */
    suspend fun isAssessmentDataStale(): Boolean {
        val timestamp = getAssessmentStatsTimestamp()
        val currentTime = System.currentTimeMillis()
        val fiveMinutesInMillis = 5 * 60 * 1000L
        return (currentTime - timestamp) > fiveMinutesInMillis
    }
}
