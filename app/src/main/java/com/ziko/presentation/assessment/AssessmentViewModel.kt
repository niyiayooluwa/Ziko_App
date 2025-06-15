package com.ziko.presentation.assessment

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziko.data.model.AssessmentDataProvider
import com.ziko.domain.usecase.AuthUseCase
import com.ziko.ui.model.AssessmentScreenContent
import com.ziko.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentCompletionViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: AuthUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")
    private val newScore: Int = savedStateHandle["score"] ?: 0

    private val _scoreImprovement = MutableStateFlow(0)
    val scoreImprovement: StateFlow<Int> = _scoreImprovement.asStateFlow()

    private val _previousScore = MutableStateFlow<Int?>(null)
    val previousScore: StateFlow<Int?> = _previousScore.asStateFlow()

    private val _submissionStatus = MutableStateFlow<SubmissionStatus>(SubmissionStatus.IDLE)
    val submissionStatus: StateFlow<SubmissionStatus> = _submissionStatus.asStateFlow()

    init {
        calculateScoreImprovement()
    }

    private fun calculateScoreImprovement() {
        viewModelScope.launch {
            try {
                // Get previous score from cache
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

    fun submitResults() {
        if (_submissionStatus.value == SubmissionStatus.SUBMITTING) return

        viewModelScope.launch {
            try {
                _submissionStatus.value = SubmissionStatus.SUBMITTING

                val token = dataStoreManager.getToken.first()
                if (token == null) {
                    Log.e("AssessmentCompletion", "No token found")
                    //_submissionStatus.value = SubmissionStatus.ERROR("No authentication token")
                    return@launch
                }

                val lessonTitle = getLessonTitleFromId(lessonId)
                Log.d("AssessmentCompletion", "Submitting score: $newScore for lesson: $lessonTitle")

                val result = repository.updateHighestScore(token, lessonTitle, newScore)

                if (result.isSuccess) {
                    Log.d("AssessmentCompletion", "Score submitted successfully")
                    _submissionStatus.value = SubmissionStatus.SUCCESS

                    // Update cached data to reflect the new score immediately
                    updateCachedScore()
                } else {
                    Log.e("AssessmentCompletion", "Score submission failed: ${result.exceptionOrNull()?.message}")
                    //_submissionStatus.value = SubmissionStatus.ERROR(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.e("AssessmentCompletion", "Score submission exception", e)
                //_submissionStatus.value = SubmissionStatus.ERROR(e.message ?: "Unknown error")
            }
        }
    }

    private suspend fun updateCachedScore() {
        try {
            val cachedStats = dataStoreManager.getCachedAssessmentStats.first()?.toMutableList()
            if (cachedStats != null) {
                val lessonIndex = cachedStats.indexOfFirst { it.id.equals(lessonId, ignoreCase = true) }
                if (lessonIndex != -1) {
                    // Update the cached score if the new score is higher
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

    fun retry() {
        _submissionStatus.value = SubmissionStatus.IDLE
        submitResults()
    }
}

enum class SubmissionStatus (val message: String? = null) {
    IDLE,
    SUBMITTING,
    SUCCESS,
    ERROR
}

// Extension function to handle error messages
fun SubmissionStatus.getErrorMessage(): String? {
    return when (this) {
        SubmissionStatus.ERROR -> this.message
        else -> null
    }
}


@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")

    private val _screens = mutableStateOf<List<AssessmentScreenContent>>(emptyList())
    val screens: List<AssessmentScreenContent> get() = _screens.value

    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> get() = _currentIndex

    // Score Tracking State
    private val _correctAnswers = mutableIntStateOf(0)
    val correctAnswers: State<Int> get() = _correctAnswers

    private val _incorrectAnswers = mutableIntStateOf(0)
    val incorrectAnswers: State<Int> get() = _incorrectAnswers

    private val totalQuestions: Int get() = _screens.value.size

    val percentageCorrect: Float
        get() = if (totalQuestions > 0) {
            (_correctAnswers.intValue.toFloat() / totalQuestions) * 100
        } else {
            0f
        }

    val currentScreen: AssessmentScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    val totalScreens: Int
        get() = _screens.value.size

    // Navigation Event State
    private val _navigateToResults = mutableStateOf<AssessmentResult?>(null)
    val navigateToResults: State<AssessmentResult?> = _navigateToResults

    // Time tracking
    private var startTimeMillis: Long = 0L

    init {
        _screens.value = AssessmentDataProvider.getAssessmentContent(lessonId)
    }

    fun updateScore(isCorrect: Boolean) {
        if (isCorrect) {
            _correctAnswers.intValue++
        } else {
            _incorrectAnswers.intValue++
        }
    }

    fun nextScreen() {
        if (_currentIndex.intValue < _screens.value.lastIndex) {
            _currentIndex.value += 1
        } else {
            // Assessment finished! Create result with all needed data
            _navigateToResults.value = AssessmentResult(
                lessonId = lessonId,
                score = percentageCorrect.toInt(),
                correctAnswers = _correctAnswers.intValue,
                totalQuestions = totalQuestions,
                timeSpent = getTimeSpent()
            )
        }
    }

    fun previousScreen() {
        if (_currentIndex.intValue > 0) {
            _currentIndex.value -= 1
        }
    }

    fun reset() {
        _currentIndex.intValue = 0
        _correctAnswers.intValue = 0
        _incorrectAnswers.intValue = 0
        _navigateToResults.value = null
        startTimeMillis = 0L
    }

    // Function to acknowledge and clear the navigation event after it's handled by UI
    fun onResultsNavigated() {
        _navigateToResults.value = null
    }

    fun startAssessment() {
        startTimeMillis = System.currentTimeMillis()
    }

    fun getTimeSpent(): String {
        if (startTimeMillis == 0L) return "0mins, 0secs"

        val elapsedMillis = System.currentTimeMillis() - startTimeMillis
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        return "${minutes}mins, ${seconds}secs"
    }
}

// Data class to hold assessment results
data class AssessmentResult(
    val lessonId: String,
    val score: Int,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val timeSpent: String
)