package com.ziko.ui.model

data class PracticeScreenContent(
    val exerciseId: String,
    val title: String,
    val description: String,
    val instructions: String,
    val options: List<String>, // Example of multiple choice options or other interactive elements
    val correctAnswer: String // Correct answer (for validation)
)
