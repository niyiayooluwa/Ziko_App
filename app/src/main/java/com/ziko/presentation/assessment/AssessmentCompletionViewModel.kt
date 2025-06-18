package com.ziko.presentation.assessment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.core.datastore.DataStoreManager
import com.ziko.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for handling the submission and evaluation of assessment results.
 *
 * It retrieves cached scores, compares with new scores, and submits results via a repository.
 *
 * @param dataStoreManager Used to access cached data and user token.
 * @param repository Domain-level use case for submitting scores.
 * @param savedStateHandle Used to retrieve navigation arguments like lessonId and score.
 */
@HiltViewModel
class AssessmentCompletionViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: AuthUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel()
{

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")
    private val newScore: Int = savedStateHandle["score"] ?: 0

    // Tracks improvement from user's previous high score
    private val _scoreImprovement = MutableStateFlow(0)

    /** Public read-only access to score improvement */
    val scoreImprovement: StateFlow<Int> = _scoreImprovement.asStateFlow()

    // Holds the user's previous score (nullable)
    private val _previousScore = MutableStateFlow<Int?>(null)

    /** Public access to previous score state */
    val previousScore: StateFlow<Int?> = _previousScore.asStateFlow()

    // Status of the current score submission process
    private val _submissionStatus = MutableStateFlow(SubmissionStatus.IDLE)

    /** Public access to submission status */
    val submissionStatus: StateFlow<SubmissionStatus> = _submissionStatus.asStateFlow()

    init {
        calculateScoreImprovement()
    }

    /**
     * Compares the current score with previously cached score.
     * Updates [_scoreImprovement] and [_previousScore].
     */
    private fun calculateScoreImprovement() {
        viewModelScope.launch {
            try {
                val cachedStats = dataStoreManager.getCachedAssessmentStats.first()
                val previousHighScore = cachedStats?.find {
                    it.id.equals(lessonId, ignoreCase = true)
                }?.highestScore ?: 0

                _previousScore.value = previousHighScore
                _scoreImprovement.value = newScore - previousHighScore

                Log.d("AssessmentCompletion", "Previous score: $previousHighScore, New score: $newScore, Improvement: ${_scoreImprovement.value}")
            } catch (e: Exception) {
                Log.e("AssessmentCompletion", "Error calculating score improvement", e)
                _scoreImprovement.value = 0
            }
        }
    }

    /**
     * Submits the new assessment result to the server.
     * Handles token fetching, submission, and state update.
     */
    fun submitResults() {
        if (_submissionStatus.value == SubmissionStatus.SUBMITTING) return

        viewModelScope.launch {
            try {
                _submissionStatus.value = SubmissionStatus.SUBMITTING

                val token = dataStoreManager.getToken.first()
                if (token == null) {
                    Log.e("AssessmentCompletion", "No token found")
                    return@launch
                }

                val lessonTitle = getLessonTitleFromId(lessonId)
                Log.d("AssessmentCompletion", "Submitting score: $newScore for lesson: $lessonTitle")

                val result = repository.updateHighestScore(token, lessonTitle, newScore)

                if (result.isSuccess) {
                    Log.d("AssessmentCompletion", "Score submitted successfully")
                    _submissionStatus.value = SubmissionStatus.SUCCESS
                    updateCachedScore()
                } else {
                    Log.e("AssessmentCompletion", "Score submission failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e("AssessmentCompletion", "Score submission exception", e)
            }
        }
    }

    /**
     * Updates the locally cached score if the new score is higher.
     */
    private suspend fun updateCachedScore() {
        try {
            val cachedStats = dataStoreManager.getCachedAssessmentStats.first()?.toMutableList()
            if (cachedStats != null) {
                val lessonIndex = cachedStats.indexOfFirst { it.id.equals(lessonId, ignoreCase = true) }
                if (lessonIndex != -1) {
                    val currentLesson = cachedStats[lessonIndex]
                    if (newScore > currentLesson.highestScore!!) {
                        cachedStats[lessonIndex] = currentLesson.copy(highestScore = newScore)
                        dataStoreManager.saveAssessmentStats(cachedStats)
                        Log.d("AssessmentCompletion", "Updated cached score for $lessonId")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AssessmentCompletion", "Error updating cached score", e)
        }
    }

    /**
     * Maps internal lesson IDs to their corresponding lesson titles used by the backend.
     */
    private fun getLessonTitleFromId(lessonId: String): String {
        return when (lessonId) {
            "lesson1" -> "monophthongs"
            "lesson2" -> "diphthongs"
            "lesson3" -> "triphthongs"
            "lesson4" -> "voiced-consonants"
            "lesson5" -> "voiceless-consonants"
            "lesson6" -> "intonation"
            "lesson7" -> "stress"
            "lesson8" -> "rhythm"
            else -> error("Unknown lessonId: $lessonId")
        }
    }

    /**
     * Resets submission state and re-attempts score submission.
     */
    fun retry() {
        _submissionStatus.value = SubmissionStatus.IDLE
        submitResults()
    }
}

/**
 * Represents different states of the score submission process.
 */
enum class SubmissionStatus(val message: String? = null) {
    IDLE,
    SUBMITTING,
    SUCCESS,
    ERROR
}

/**
 * Returns the error message if the status is [SubmissionStatus.ERROR], otherwise null.
 */
fun SubmissionStatus.getErrorMessage(): String? {
    return when (this) {
        SubmissionStatus.ERROR -> this.message
        else -> null
    }
}