package com.ziko.data.local.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment4Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Pronounce the word",
            displayText = "jungle",
            expectedText = "jungle",
            audioPath = "lesson/lesson4/jungle.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 2,
            instructions = "Pronounce the word",
            displayText = "valley",
            expectedText = "valley",
            audioPath = "lesson/lesson4/valley.mp3"
        ),
        AssessmentScreenContent.SpeakAssessment(
            id = 3,
            instructions = "Pronounce the word",
            displayText = "table",
            expectedText = "table",
            audioPath = "lesson/lesson4/table.mp3"
        ),
        AssessmentScreenContent.SpeakAssessment(
            id = 4,
            instructions = "Pronounce the word",
            displayText = "mother",
            expectedText = "mother",
            audioPath = "lesson/lesson4/mother.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 5,
            instructions = "Pronounce the word",
            displayText = "zip",
            expectedText = "zip",
            audioPath = "lesson/lesson4/zip.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 6,
            instructions = "Pronounce the word pair",
            displayText = "cap - cab",
            expectedText = "cap cab",
            audioPath = "lesson/lesson4/cap_cab.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 7,
            instructions = "Pronounce the word pair",
            displayText = "ten - den",
            expectedText = "ten den",
            audioPath = "lesson/lesson4/ten_den.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 8,
            instructions = "Pronounce the word pair",
            displayText = "price - prize",
            expectedText = "price prize",
            audioPath = "lesson/lesson4/price_prize.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 9,
            instructions = "Speak this sentence",
            displayText = "The van moved very fast through the valley",
            expectedText = "The van moved very fast through the valley",
            audioPath = "lesson/lesson4/the_van_moved_very_fast.mp3"
        )
    )
}