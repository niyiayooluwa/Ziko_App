package com.ziko.presentation.home

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.domain.model.AssessmentCardInfo
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.core.datastore.DataStoreManager
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

/**
 * ViewModel responsible for managing assessment statistics data.
 *
 * This class:
 * - Loads cached data first to improve UX
 * - Fetches fresh data from backend when needed
 * - Caches new data for offline use
 * - Periodically refreshes data every 5 minutes
 * - Exposes a public state to be consumed by the UI layer
 *
 * @param repository Use case that handles authenticated API operations.
 * @param dataStoreManager Manages local storage and caching.
 * @param connectivityManager Used to check network status before attempting remote fetches.
 */
@HiltViewModel
class AssessmentStatsViewModel @Inject constructor(
    private val repository: AuthUseCase,
    private val dataStoreManager: DataStoreManager,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    // Internal state holding both data and metadata (loading/error status)
    private val _assessmentDataState = MutableStateFlow(
        AssessmentDataState(
            data = emptyList(),
            status = AssessmentDataStatus.LOADING
        )
    )

    /**
     * Public read-only flow of full assessment data state.
     * UI uses this to render cards and display progress.
     */
    val assessmentDataState: StateFlow<AssessmentDataState> = _assessmentDataState.asStateFlow()

    /**
     * Extracts only the data (list of assessment cards) from [assessmentDataState].
     * Meant as a backward-compatible and simplified data source for UI.
     */
    val assessmentStats: StateFlow<List<AssessmentCardInfo>> = assessmentDataState.map { it.data }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Public error state exposed for UI to observe failure messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAssessmentData()
        startPeriodicRefresh() // auto-refresh every 5 minutes if needed
    }

    /**
     * Loads cached assessment stats if available.
     * If data is stale or unavailable, triggers a backend fetch.
     */
    private fun loadAssessmentData() {
        viewModelScope.launch {
            try {
                val cachedData = dataStoreManager.getCachedAssessmentStats.first()
                val isDataStale = dataStoreManager.isAssessmentDataStale()

                if (cachedData != null) {
                    _assessmentDataState.value = AssessmentDataState(
                        data = cachedData,
                        status = if (isDataStale) AssessmentDataStatus.CACHED else AssessmentDataStatus.UPDATED,
                        lastUpdated = dataStoreManager.getAssessmentStatsTimestamp()
                    )
                }

                if (cachedData == null || isDataStale) {
                    fetchFreshData(cachedData != null)
                }

            } catch (e: Exception) {
                _assessmentDataState.value = AssessmentDataState(
                    data = defaultLessons,
                    status = AssessmentDataStatus.ERROR,
                    errorMessage = e.message
                )
            }
        }
    }

    /**
     * Attempts to fetch the latest stats from the backend and update local cache and UI.
     *
     * @param hasCachedData Whether cached data is already shown (affects loading state shown)
     */
    private suspend fun fetchFreshData(hasCachedData: Boolean) {
        if (!isNetworkAvailable()) {
            _assessmentDataState.value = _assessmentDataState.value.copy(
                status = AssessmentDataStatus.OFFLINE
            )
            return
        }

        if (hasCachedData) {
            _assessmentDataState.value = _assessmentDataState.value.copy(
                status = AssessmentDataStatus.UPDATING
            )
        }

        try {
            val token = dataStoreManager.getToken.first() ?: ""

            repository.getAssessmentStats(token)
                .onSuccess { backendItems ->
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

                    dataStoreManager.saveAssessmentStats(updatedStats)

                    _assessmentDataState.value = AssessmentDataState(
                        data = updatedStats,
                        status = AssessmentDataStatus.UPDATED,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                .onFailure { exception ->
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
            val currentData = _assessmentDataState.value.data.ifEmpty { defaultLessons }
            _assessmentDataState.value = AssessmentDataState(
                data = currentData,
                status = AssessmentDataStatus.ERROR,
                errorMessage = e.message
            )
        }
    }

    /**
     * Checks whether the device has any usable network connection.
     */
    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    /**
     * Periodically refreshes assessment data every 5 minutes *if* the data is stale.
     */
    private fun startPeriodicRefresh() {
        viewModelScope.launch {
            while (true) {
                delay(5 * 60 * 1000)
                if (dataStoreManager.isAssessmentDataStale()) {
                    fetchFreshData(true)
                }
            }
        }
    }

    /**
     * Public method for manually refreshing assessment data, regardless of cache state.
     */
    fun refreshData() {
        viewModelScope.launch {
            fetchFreshData(true)
        }
    }

    /**
     * Retrieves the highest score for a given lesson title.
     * Used to populate individual [AssessmentCardInfo] dynamically.
     *
     * @param lessonTitle Title of the lesson to search for.
     * @return Score percentage or null if not found.
     */
    suspend fun getLessonHighScore(lessonTitle: String): Int? {
        return dataStoreManager.getLessonHighScoreWithTitle(lessonTitle)
    }

    /**
     * Retrieves the highest score using the lesson's unique identifier.
     *
     * @param lessonId The backend/internal ID of the lesson.
     * @return Score percentage or null if not found.
     */
    suspend fun getLessonHighScoreById(lessonId: String): Int? {
        return dataStoreManager.getLessonHighScoreWithId(lessonId)
    }

    /**
     * The default set of lessons used when no data is available.
     * These are merged with backend stats to produce [AssessmentCardInfo]s.
     */
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
