package com.ziko.data.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment1Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Pronounce the word pair",
            expectedText = "bit beat",
            displayText = "bit - beat",
            audioPath = "lesson/lesson1/bit_beat.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 2,
            instructions = "Pronounce the word pair",
            expectedText = "full fool",
            displayText = "full - fool",
            audioPath = "lesson/lesson1/full_fool.mp3"
        ),
        AssessmentScreenContent.SpeakAssessment(
            id = 3,
            instructions = "Pronounce the word pair",
            expectedText = "hot heart",
            displayText = "hot - heart",
            audioPath = "lesson/lesson1/hot_heart.mp3"
        ),
    )
}
