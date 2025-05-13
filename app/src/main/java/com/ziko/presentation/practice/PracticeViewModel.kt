package com.ziko.presentation.practice

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class PracticeViewModel @HiltViewModel constructor(
    private val repository: PracticeRepository
) : ViewModel() {
    // Holds the current practice exercise's data
    private val _currentPracticeExercise = mutableStateOf<PracticeExercise?>(null)
    val currentPracticeExercise: State<PracticeExercise?> = _currentPracticeExercise

    // Progress for the current practice exercise
    private val _progress = mutableStateOf(0f)
    val progress: State<Float> = _progress

    // Error state
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadPracticeExercise(lessonId: String, exerciseId: String) {
        // Fetch the exercise from the repository
        viewModelScope.launch {
            try {
                _currentPracticeExercise.value = repository.getPracticeExercise(lessonId, exerciseId)
                _progress.value = 0f // Reset progress
            } catch (e: Exception) {
                _error.value = "Failed to load practice exercise"
            }
        }
    }

    fun updateProgress(newProgress: Float) {
        _progress.value = newProgress.coerceIn(0f, 1f)
    }

    fun nextExercise() {
        // Logic to go to the next exercise, if any
    }
}
