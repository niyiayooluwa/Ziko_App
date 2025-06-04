package com.ziko.presentation.assessment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.remote.AssessmentCardInfo
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentStatsViewModel @Inject constructor(
    private val repository: AuthUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _assessmentStats = MutableStateFlow<List<AssessmentCardInfo>>(emptyList())
    val assessmentStats: StateFlow<List<AssessmentCardInfo>> = _assessmentStats.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchStats()
    }

    private fun fetchStats() {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.first() ?: ""
            Log.d("AssessmentStatsVM", "Fetched token: $token")
            repository.getAssessmentStats(token)
                .onSuccess { backendItems ->
                    Log.d("AssessmentStatsVM", "Backend raw data: $backendItems")
                    Log.d("AssessmentStatsVM", "Backend response size: ${backendItems.size}")

                    val statsMap = backendItems.associateBy { it.title.trim().lowercase() }

                    _assessmentStats.value = defaultLessons.map { lesson ->
                        val key = lesson.title.trim().lowercase()
                        statsMap[key]?.let { stat ->
                            Log.d("AssessmentStatsVM", "Match found for '${lesson.title}' with score ${stat.highestScore} and acc ${stat.accuracy}")
                            lesson.copy(
                                highestScore = stat.highestScore,
                                accuracy = stat.accuracy
                            )
                        } ?: run {
                            Log.d("AssessmentStatsVM", "No stats found for lesson '${lesson.title}', using default")
                            lesson
                        }
                    }
                }
                .onFailure {
                    Log.e("AssessmentStatsVM", "Failed to fetch stats", it)
                    _assessmentStats.value = defaultLessons
                }

        }
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



