package com.ziko.presentation.assessment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ziko.core.datastore.DataStoreManager
import com.ziko.data.model.AssessmentDataProvider
import com.ziko.ui.model.AssessmentScreenContent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for handling the assessment session.
 *
 * Controls question navigation, scoring, result aggregation, and time tracking.
 *
 * @param dataStoreManager Provides user preferences or lesson info.
 * @param savedStateHandle Used to retrieve the current lesson ID.
 */
@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")

    // List of screens/questions for the current assessment
    private val _screens = mutableStateOf<List<AssessmentScreenContent>>(emptyList())
    //val screens: List<AssessmentScreenContent> get() = _screens.value

    // Index of current screen
    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> get() = _currentIndex

    // Score counters
    private val _correctAnswers = mutableIntStateOf(0)
    val correctAnswers: State<Int> get() = _correctAnswers

    private val _incorrectAnswers = mutableIntStateOf(0)
    //val incorrectAnswers: State<Int> get() = _incorrectAnswers

    // Derived value: total questions
    private val totalQuestions: Int get() = _screens.value.size

    /** Returns the percentage of correct answers so far */
    val percentageCorrect: Float
        get() = if (totalQuestions > 0) {
            (_correctAnswers.intValue.toFloat() / totalQuestions) * 100
        } else {
            0f
        }

    /** Current screen user is on */
    val currentScreen: AssessmentScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    /** Current progress (from 0 to 1) */
    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    /** Total number of screens */
    val totalScreens: Int
        get() = _screens.value.size

    // Flag for when assessment ends and should navigate to results
    private val _navigateToResults = mutableStateOf<AssessmentResult?>(null)
    val navigateToResults: State<AssessmentResult?> = _navigateToResults

    // Time tracking
    private var startTimeMillis: Long = 0L

    init {
        _screens.value = AssessmentDataProvider.getAssessmentContent(lessonId)
    }

    /**
     * Updates score based on user's response.
     *
     * @param isCorrect whether the selected answer was correct.
     */
    fun updateScore(isCorrect: Boolean) {
        if (isCorrect) _correctAnswers.intValue++ else _incorrectAnswers.intValue++
    }

    /**
     * Moves to next screen or triggers result screen navigation if at end.
     */
    fun nextScreen() {
        if (_currentIndex.intValue < _screens.value.lastIndex) {
            _currentIndex.value += 1
        } else {
            _navigateToResults.value = AssessmentResult(
                lessonId = lessonId,
                score = percentageCorrect.toInt(),
                correctAnswers = _correctAnswers.intValue,
                totalQuestions = totalQuestions,
                timeSpent = getTimeSpent()
            )
        }
    }

    /**
     * Moves to previous question in the assessment.
     */
    fun previousScreen() {
        if (_currentIndex.intValue > 0) {
            _currentIndex.value -= 1
        }
    }

    /**
     * Resets all progress and score states.
     */
    /*fun reset() {
        _currentIndex.intValue = 0
        _correctAnswers.intValue = 0
        _incorrectAnswers.intValue = 0
        _navigateToResults.value = null
        startTimeMillis = 0L
    }*/

    /**
     * Call this after navigating to results to avoid re-triggering navigation.
     */
    fun onResultsNavigated() {
        _navigateToResults.value = null
    }

    /**
     * Starts the timer for the assessment.
     */
    fun startAssessment() {
        startTimeMillis = System.currentTimeMillis()
    }

    /**
     * Calculates the duration of the assessment in mins and secs.
     *
     * @return a formatted string like "3mins, 12secs"
     */
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