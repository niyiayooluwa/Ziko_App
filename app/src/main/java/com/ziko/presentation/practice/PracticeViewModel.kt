package com.ziko.presentation.practice

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ziko.data.model.PracticeDataProvider
import com.ziko.ui.model.PracticeScreenContent

/**
 * ViewModel responsible for managing the practice session state for a given lesson.
 *
 * It provides navigation between screens, exposes progress, and loads the screen content
 * from a data provider using the passed `lessonId`.
 *
 * @param savedStateHandle Used to retrieve the `lessonId` argument passed via navigation.
 *
 * @throws IllegalArgumentException if `lessonId` is missing in [savedStateHandle].
 */
class PracticeViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // The ID of the current lesson, used to fetch relevant practice content
    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")

    // Backing state for the list of practice screens
    private val _screens = mutableStateOf<List<PracticeScreenContent>>(emptyList())

    /** Public read-only access to the loaded practice screens */
    val screens: List<PracticeScreenContent> get() = _screens.value

    // Current index in the practice screen flow
    private val _currentIndex = mutableIntStateOf(0)

    /** Public read-only state for the current screen index */
    val currentIndex: State<Int> = _currentIndex

    /** The screen currently being displayed, or null if index is out of bounds */
    val currentScreen: PracticeScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    /**
     * The current progress in the session, represented as a float from 0 to 1.
     *
     * - If there are no screens loaded, returns 0.
     * - Otherwise, returns the index + 1 divided by the total number of screens.
     */
    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    /** Total number of practice screens in the current session */
    val totalScreens: Int
        get() = _screens.value.size

    init {
        // Load screens using the lesson ID
        _screens.value = PracticeDataProvider.getPracticeContent(lessonId)
    }

    /**
     * Move to the next screen in the sequence.
     *
     * If the user is on the last screen, triggers [onFinished] instead.
     *
     * @param onFinished Callback invoked when the user finishes the last screen.
     */
    fun nextScreen(onFinished: () -> Unit) {
        if (_currentIndex.intValue < _screens.value.lastIndex) {
            _currentIndex.value += 1
        } else {
            onFinished()
        }
    }

    /**
     * Navigate back to the previous screen, if not at the beginning.
     */
    fun previousScreen() {
        if (_currentIndex.intValue > 0) {
            _currentIndex.value -= 1
        }
    }

    /**
     * Reset the current session, moving back to the first screen.
     */
    fun reset() {
        _currentIndex.intValue = 0
    }
}

/**
 * Factory for creating [PracticeViewModel] instances with a [SavedStateHandle].
 *
 * Required when using navigation components that provide the handle automatically.
 *
 * @param savedStateHandle The navigation-backed state handle containing `lessonId`.
 */
class PracticeViewModelFactory(
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PracticeViewModel::class.java)) {
            return PracticeViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
