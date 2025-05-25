package com.ziko.presentation.assessment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ziko.data.model.AssessmentDataProvider
import com.ziko.ui.model.AssessmentScreenContent

class AssessmentViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: String = savedStateHandle["lessonId"] ?: error("Missing lessonId")

    private val _screens = mutableStateOf<List<AssessmentScreenContent>>(emptyList())
    val screens: List<AssessmentScreenContent> get() = _screens.value

    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> = _currentIndex

    val currentScreen: AssessmentScreenContent?
        get() = _screens.value.getOrNull(_currentIndex.intValue)

    val progress: Float
        get() = if (_screens.value.isEmpty()) 0f else (_currentIndex.intValue + 1).toFloat() / _screens.value.size

    val totalScreens: Int
        get() = _screens.value.size

    init {
        _screens.value = AssessmentDataProvider.getAssessmentContent(lessonId)
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


class AssessmentViewModelFactory(
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssessmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssessmentViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}