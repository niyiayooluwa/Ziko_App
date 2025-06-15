package com.ziko.presentation.home

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.remote.AssessmentCardInfo
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentStatsViewModel @Inject constructor(
    private val repository: AuthUseCase,
    private val dataStoreManager: DataStoreManager,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _assessmentDataState = MutableStateFlow(
        AssessmentDataState(
            data = emptyList(),
            status = AssessmentDataStatus.LOADING
        )
    )
    val assessmentDataState: StateFlow<AssessmentDataState> = _assessmentDataState.asStateFlow()

    // Convenience property for backward compatibility
    val assessmentStats: StateFlow<List<AssessmentCardInfo>> = assessmentDataState.map { it.data }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAssessmentData()
        // Set up periodic refresh every 5 minutes when app is active
        startPeriodicRefresh()
    }

    private fun loadAssessmentData() {
        viewModelScope.launch {
            try {
                // Step 1: Load cached data first
                val cachedData = dataStoreManager.getCachedAssessmentStats.first()
                val isDataStale = dataStoreManager.isAssessmentDataStale()

                if (cachedData != null) {
                    // Show cached data immediately
                    _assessmentDataState.value = AssessmentDataState(
                        data = cachedData,
                        status = if (isDataStale) AssessmentDataStatus.CACHED else AssessmentDataStatus.UPDATED,
                        lastUpdated = dataStoreManager.getAssessmentStatsTimestamp()
                    )
                    Log.d("AssessmentStatsVM", "Loaded cached data: ${cachedData.size} items")
                }

                // Step 2: Fetch fresh data if needed
                if (cachedData == null || isDataStale) {
                    fetchFreshData(cachedData != null)
                }

            } catch (e: Exception) {
                Log.e("AssessmentStatsVM", "Error loading assessment data", e)
                _assessmentDataState.value = AssessmentDataState(
                    data = defaultLessons,
                    status = AssessmentDataStatus.ERROR,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun fetchFreshData(hasCachedData: Boolean) {
        if (!isNetworkAvailable()) {
            _assessmentDataState.value = _assessmentDataState.value.copy(
                status = AssessmentDataStatus.OFFLINE
            )
            return
        }

        // Update status to show we're fetching
        if (hasCachedData) {
            _assessmentDataState.value = _assessmentDataState.value.copy(
                status = AssessmentDataStatus.UPDATING
            )
        }

        try {
            val token = dataStoreManager.getToken.first() ?: ""
            Log.d("AssessmentStatsVM", "Fetching fresh data with token: $token")

            repository.getAssessmentStats(token)
                .onSuccess { backendItems ->
                    Log.d("AssessmentStatsVM", "Backend response: ${backendItems.size} items")

                    val statsMap = backendItems.associateBy { it.title.trim().lowercase() }
                    val updatedStats = defaultLessons.map { lesson ->
                        val key = lesson.title.trim().lowercase()
                        statsMap[key]?.let { stat ->
                            lesson.copy(
                                highestScore = stat.highestScore,
                                accuracy = stat.accuracy
                            )
                        } ?: lesson
                    }

                    // Save to cache
                    dataStoreManager.saveAssessmentStats(updatedStats)

                    // Update UI
                    _assessmentDataState.value = AssessmentDataState(
                        data = updatedStats,
                        status = AssessmentDataStatus.UPDATED,
                        lastUpdated = System.currentTimeMillis()
                    )

                    Log.d("AssessmentStatsVM", "Successfully updated assessment data")
                }
                .onFailure { exception ->
                    Log.e("AssessmentStatsVM", "Failed to fetch fresh data", exception)

                    val currentData = _assessmentDataState.value.data.ifEmpty { defaultLessons }
                    _assessmentDataState.value = AssessmentDataState(
                        data = currentData,
                        status = AssessmentDataStatus.ERROR,
                        lastUpdated = _assessmentDataState.value.lastUpdated,
                        errorMessage = exception.message
                    )
                    _error.value = exception.message
                }

        } catch (e: Exception) {
            Log.e("AssessmentStatsVM", "Exception in fetchFreshData", e)
            val currentData = _assessmentDataState.value.data.ifEmpty { defaultLessons }
            _assessmentDataState.value = AssessmentDataState(
                data = currentData,
                status = AssessmentDataStatus.ERROR,
                errorMessage = e.message
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    private fun startPeriodicRefresh() {
        viewModelScope.launch {
            while (true) {
                delay(5 * 60 * 1000) // 5 minutes
                if (dataStoreManager.isAssessmentDataStale()) {
                    fetchFreshData(true)
                }
            }
        }
    }

    // Public method to manually refresh data
    fun refreshData() {
        viewModelScope.launch {
            fetchFreshData(true)
        }
    }

    // Method to get a specific lesson's high score
    suspend fun getLessonHighScore(lessonTitle: String): Int? {
        return dataStoreManager.getLessonHighScoreWithTitle(lessonTitle)
    }

    suspend fun getLessonHighScoreById(lessonId: String): Int? {
        return dataStoreManager.getLessonHighScoreWithId(lessonId)
    }

    private val defaultLessons = listOf(
        AssessmentCardInfo("Monophthongs", "lesson1"),
        AssessmentCardInfo("Diphthongs", "lesson2"),
        AssessmentCardInfo("Triphthongs", "lesson3"),
        AssessmentCardInfo("Voiced Consonants", "lesson4"),
        AssessmentCardInfo("Voiceless Consonants", "lesson5"),
        AssessmentCardInfo("Intonation", "lesson6"),
        AssessmentCardInfo("Stress", "lesson7"),
        AssessmentCardInfo("Rhythm", "lesson8"),
    )
}