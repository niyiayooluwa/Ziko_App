package com.ziko.ui.model

data class PracticeScreenContent(
    val id: Int,
    val instructions: String,
    val sound: Pair<String, String>? = null,
    val expectedPhrase: String, // Correct answer (for validation)
)
