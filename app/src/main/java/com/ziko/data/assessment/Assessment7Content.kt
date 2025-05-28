package com.ziko.data.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment7Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.McqAssessment(
            id = 1,
            instructions = "Select the correct word stress",
            question = "remarkable",
            options = listOf("reMARKable", "REmarkABLE"),
            correctAnswer = "REmarkABLE",
        ),

        AssessmentScreenContent.McqAssessment(
            id = 2,
            instructions = "Select the correct word stress",
            question = "encouraging",
            options = listOf("enCOURaging", "encouRAGING"),
            correctAnswer = "encouRAGING",
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 3,
            instructions = "Pronounce the word",
            displayText = "photograph",
            expectedText = "photograph",
            audioPath = "lesson/lesson7/photograph.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 4,
            instructions = "Pronounce the word",
            displayText = "photography",
            expectedText = "photography",
            audioPath = "lesson/lesson7/photography.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 5,
            instructions = "Pronounce the word",
            displayText = "photographic",
            expectedText = "photographic",
            audioPath = "lesson/lesson7/photographic.mp3"
        ),
    )
}