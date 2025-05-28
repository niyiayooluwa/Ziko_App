package com.ziko.data.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment5Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Pronounce the word",
            displayText = "park",
            expectedText = "park",
            audioPath = "lesson/lesson5/park.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 2,
            instructions = "Pronounce the word",
            displayText = "thin",
            expectedText = "thin",
            audioPath = "lesson/lesson5/thin.mp3"
        ),
        AssessmentScreenContent.SpeakAssessment(
            id = 3,
            instructions = "Pronounce the word",
            displayText = "fish",
            expectedText = "fish",
            audioPath = "lesson/lesson5/fish.mp3"
        ),
        AssessmentScreenContent.SpeakAssessment(
            id = 4,
            instructions = "Pronounce the word",
            displayText = "church",
            expectedText = "church",
            audioPath = "lesson/lesson5/church.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 5,
            instructions = "Pronounce the word",
            displayText = "zip",
            expectedText = "zip",
            audioPath = "lesson/lesson5/zip.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 6,
            instructions = "Pronounce the word",
            displayText = "black",
            expectedText = "black",
            audioPath = "lesson/lesson5/black.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 7,
            instructions = "Pronounce the word pair",
            displayText = "buzz - bus",
            expectedText = "buzz bus",
            audioPath = "lesson/lesson5/buzz_bus.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 8,
            instructions = "Pronounce the word pair",
            displayText = "dog - duck",
            expectedText = "dog duck",
            audioPath = "lesson/lesson5/dog_duck.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 9,
            instructions = "Pronounce the word pair",
            displayText = "van - fan",
            expectedText = "van fan",
            audioPath = "lesson/lesson5/van_fan.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 10,
            instructions = "Speak this sentence",
            displayText = "The thick fog covered the farm early in the morning",
            expectedText = "The thick fog covered the farm early in the morning",
            audioPath = "lesson/lesson5/the_thick_fog.mp3"
        )
    )
}