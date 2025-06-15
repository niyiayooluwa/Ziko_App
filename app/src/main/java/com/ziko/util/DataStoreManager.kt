package com.ziko.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.ziko.data.remote.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.google.gson.reflect.TypeToken
import com.ziko.data.remote.AssessmentCardInfo

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val dataStore = context.dataStore  // uses the top-level singleton
    private val PROFILE_PIC_URI = stringPreferencesKey("profile_pic_uri")
    private val ASSESSMENT_STATS_KEY = stringPreferencesKey("assessment_stats")
    private val ASSESSMENT_STATS_TIMESTAMP_KEY = longPreferencesKey("assessment_stats_timestamp")

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    val getToken: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    suspend fun saveProfilePicUri(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[PROFILE_PIC_URI] = uri
        }
    }

    suspend fun getProfilePicUri(): String? {
        return context.dataStore.data.first()[PROFILE_PIC_URI]
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

    // Assessment Stats Caching Methods
    suspend fun saveAssessmentStats(stats: List<AssessmentCardInfo>) {
        val statsJson = Gson().toJson(stats)
        val currentTime = System.currentTimeMillis()

        context.dataStore.edit { prefs ->
            prefs[ASSESSMENT_STATS_KEY] = statsJson
            prefs[ASSESSMENT_STATS_TIMESTAMP_KEY] = currentTime
        }
    }

    val getCachedAssessmentStats: Flow<List<AssessmentCardInfo>?> = context.dataStore.data.map { prefs ->
        prefs[ASSESSMENT_STATS_KEY]?.let { json ->
            try {
                val type = object : TypeToken<List<AssessmentCardInfo>>() {}.type
                Gson().fromJson<List<AssessmentCardInfo>>(json, type)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getAssessmentStatsTimestamp(): Long {
        return context.dataStore.data.first()[ASSESSMENT_STATS_TIMESTAMP_KEY] ?: 0L
    }

    suspend fun clearAssessmentStats() {
        context.dataStore.edit { prefs ->
            prefs.remove(ASSESSMENT_STATS_KEY)
            prefs.remove(ASSESSMENT_STATS_TIMESTAMP_KEY)
        }
    }

    // Method to get a specific lesson's high score
    suspend fun getLessonHighScoreWithTitle(lessonTitle: String): Int? {
        return getCachedAssessmentStats.first()?.find {
            it.title.equals(lessonTitle, ignoreCase = true)
        }?.highestScore
    }

    suspend fun getLessonHighScoreWithId(lessonId: String): Int? {
        return getCachedAssessmentStats.first()?.find {
            it.id.equals(lessonId, ignoreCase = true)
        }?.highestScore
    }

    // Check if cached data is stale (older than 5 minutes)
    suspend fun isAssessmentDataStale(): Boolean {
        val timestamp = getAssessmentStatsTimestamp()
        val currentTime = System.currentTimeMillis()
        val fiveMinutesInMillis = 5 * 60 * 1000L
        return (currentTime - timestamp) > fiveMinutesInMillis
    }
}
