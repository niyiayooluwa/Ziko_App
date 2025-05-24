package com.ziko.presentation.lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ziko.data.model.LessonCard
import com.ziko.data.model.LessonDataProvider
import com.ziko.ui.model.LessonScreenContent
import com.ziko.util.loadAudioManifest

class LessonViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")

    // Screens loaded for this lessons
    private val _screens = mutableStateOf<List<LessonScreenContent>>(emptyList())
    val screens: List<LessonScreenContent> get() = _screens.value

    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> = _currentIndex

    val currentScreen: LessonScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    var selectedLesson by mutableStateOf<LessonCard?>(null)
        private set

    val totalScreens: Int
        get() = _screens.value.size


    init {
        _screens.value = LessonDataProvider.getLessonContent(lessonId)
    }

    fun selectLesson(lesson: LessonCard) {
        selectedLesson = lesson
    }

    fun nextScreen(onFinished: () -> Unit) {
        if (_currentIndex.intValue < _screens.value.lastIndex) {
            _currentIndex.value += 1
        } else {
            onFinished()
        }
    }

    fun previousScreen() {
        if (_currentIndex.intValue > 0) {
            _currentIndex.value -= 1
        }
    }

    fun reset() {
        _currentIndex.value = 0
    }
}

class LessonViewModelFactory(private val savedStateHandle: SavedStateHandle) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}