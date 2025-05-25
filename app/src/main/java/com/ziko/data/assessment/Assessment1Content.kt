package com.ziko.data.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment1Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Pronounce the word",
            expectedText = "say",
            displayText = "say",
            audioPath = "lesson/lesson1/about.mp3"
        ),

        AssessmentScreenContent.McqAssessment(
            id = 2,
            instructions = "Choose the correct word with the /ɪ/ vowel.",
            question = "Which of these words has the /ɪ/ sound?",
            options = listOf("bit", "bat", "but", "bet"),
            correctAnswer = "bit"
        )
    )
}
