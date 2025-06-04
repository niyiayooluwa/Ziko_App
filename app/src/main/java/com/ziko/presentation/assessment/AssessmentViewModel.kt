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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: AuthUseCase, // Your API interface
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
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
    private val _navigateToResults = mutableStateOf<Float?>(null) // Now only Float
    val navigateToResults: State<Float?> = _navigateToResults

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
            // Assessment finished! Set the navigation event with only the percentage.
            _navigateToResults.value = percentageCorrect
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
    }

    // Function to acknowledge and clear the navigation event after it's handled by UI
    fun onResultsNavigated() {
        _navigateToResults.value = null
    }

    private fun getLessonTitleFromId(lessonId: String): String {
        return when (lessonId) {
            "lesson1" -> "monophthongs"
            "lesson2" -> "diphthongs"
            "lesson3" -> "diphthongs"
            "lesson4" -> "voiced-consonants"
            "lesson5" -> "voiceless-consonants"
            "lesson6" -> "intonation"
            "lesson7" -> "stress"
            "lesson8" -> "rhythm"
            else -> error("Unknown lessonId: $lessonId")
        }
    }

    fun submitResults(score: Int) {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.firstOrNull()
            if (token == null) {
                Log.e("AssessmentUpdate", "No token found")
                return@launch
            }

            val lesson = getLessonTitleFromId(lessonId)
            //val requestBody = ScoreUpdateRequest(score)
            Log.d("AssessmentUpdate", "Submitting score: $score for lesson: $lesson")
            try {
                val result = repository.updateHighestScore(token, lesson, score)
                if (result.isSuccess) {
                    Log.d("AssessmentUpdate", "Assessment update success")
                } else {
                    Log.e("AssessmentUpdate", "Update failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e("AssessmentUpdate", "Update exception: ${e.message}")
            }
        }
    }


    private var startTimeMillis: Long = 0L

    fun startAssessment() {
        startTimeMillis = System.currentTimeMillis()
    }

    fun getTimeSpent(): String {
        val elapsedMillis = System.currentTimeMillis() - startTimeMillis
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        return "${minutes}mins, ${seconds}secs"
    }
}
