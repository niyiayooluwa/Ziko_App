package com.ziko.presentation.lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ziko.ui.model.LessonCard
import com.ziko.data.model.LessonDataProvider
import com.ziko.ui.model.LessonScreenContent

/**
 * ViewModel responsible for managing the state of a single lesson flow.
 *
 * Handles:
 * - The current screen index.
 * - The list of lesson content screens.
 * - Navigation logic (next, previous, reset).
 * - Exposes computed progress and current screen.
 *
 * @param savedStateHandle Used to retrieve the lesson ID passed via navigation arguments.
 */
class LessonViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Retrieve lessonId from navigation arguments or crash early if missing
    private val lessonId: String = savedStateHandle["lessonId"]
        ?: error("Missing lessonId")

    // Holds all screen content for the current lesson
    private val _screens = mutableStateOf<List<LessonScreenContent>>(emptyList())

    /**
     * Publicly exposed list of lesson screens.
     */
    val screens: List<LessonScreenContent> get() = _screens.value

    // Tracks current screen index
    private val _currentIndex = mutableIntStateOf(0)

    /**
     * Publicly exposed immutable state of current index.
     */
    val currentIndex: State<Int> = _currentIndex

    /**
     * Gets the current screen being displayed, or null if index is out of bounds.
     */
    val currentScreen: LessonScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    /**
     * Progress of the lesson as a float from 0.0 to 1.0.
     * Used for progress bars and flow indication.
     */
    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f
        else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    // Optional reference to selected lesson metadata, e.g., title, image, etc.
    private var selectedLesson by mutableStateOf<LessonCard?>(null)

    /**
     * Total number of screens in the current lesson.
     */
    val totalScreens: Int
        get() = _screens.value.size

    // Load lesson content into state at init time
    init {
        _screens.value = LessonDataProvider.getLessonContent(lessonId)
    }

    /**
     * Stores lesson metadata for use in external components (optional).
     *
     * @param lesson A [LessonCard] object containing metadata like title, thumbnail, etc.
     */
    fun selectLesson(lesson: LessonCard) {
        selectedLesson = lesson
    }

    /**
     * Moves to the next screen, or triggers [onFinished] callback when all screens are done.
     *
     * @param onFinished Callback to be triggered when user finishes the last screen.
     */
    fun nextScreen(onFinished: () -> Unit) {
        if (_currentIndex.intValue < _screens.value.lastIndex) {
            _currentIndex.value += 1
        } else {
            onFinished()
        }
    }

    /**
     * Moves back to the previous screen if not already at the beginning.
     */
    fun previousScreen() {
        if (_currentIndex.intValue > 0) {
            _currentIndex.value -= 1
        }
    }

    /**
     * Resets the current screen index to the start of the lesson.
     */
    fun reset() {
        _currentIndex.intValue = 0
    }
}


/**
 * Factory class to provide a [LessonViewModel] with [SavedStateHandle] support.
 *
 * Used when a ViewModel requires navigation arguments via `savedStateHandle`.
 *
 * @property savedStateHandle The handle containing saved state and navigation arguments.
 */
class LessonViewModelFactory(
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    /**
     * Creates a ViewModel of the specified type if it's assignable from [LessonViewModel].
     *
     * @throws IllegalArgumentException if the ViewModel class is not supported.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}