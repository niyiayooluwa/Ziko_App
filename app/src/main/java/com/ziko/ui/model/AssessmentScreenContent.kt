package com.ziko.ui.model

sealed class AssessmentScreenContent {
    data class SpeakAssessment(
        val id: Int,
        val instructions: String,
        val expectedText: String,
        val displayText: String,
        val audioPath: String? = null // Optional, for playback if needed
    ) : AssessmentScreenContent()

    data class McqAssessment(
        val id: Int,
        val instructions: String,
        val question: String,
        val options: List<String>,
        val correctAnswer: String
    ) : AssessmentScreenContent()
}
