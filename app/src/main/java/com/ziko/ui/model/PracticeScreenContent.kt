package com.ziko.ui.model

data class PracticeScreenContent(
    val id: Int,
    val instructions: String,
    val options: List<String>, // Example of multiple choice options or other interactive elements
    val expectedPhrase: String // Correct answer (for validation)
)
