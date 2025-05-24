// Lesson1Content.kt
package com.ziko.data.lessons

import androidx.compose.ui.graphics.Color
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent

fun getLesson2Info(): LessonCard {
    return LessonCard(
        title = "Diphthongs",
        description = "Master gliding vowel sounds like /al/, /el/, and /əʊ/",
        id = "lesson2",
        color = Color(0xFF410FA3)
    )
}

fun getLesson2Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of Diphthongs & word examples",
            sound = "/eɪ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "day" to "lessons/lesson1/about.mp3",
                "pay" to "lessons/lesson1/about.mp3",
                "say" to "lessons/lesson1/about.mp3",
                "weight" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aɪ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "high" to "lessons/lesson1/about.mp3",
                "my" to "lessons/lesson1/about.mp3",
                "right" to "lessons/lesson1/about.mp3",
                "kite" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɔɪ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "boy" to "lessons/lesson1/about.mp3",
                "joy" to "lessons/lesson1/about.mp3",
                "toy" to "lessons/lesson1/about.mp3",
                "noise" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aʊ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "now" to "lessons/lesson1/about.mp3",
                "cow" to "lessons/lesson1/about.mp3",
                "house" to "lessons/lesson1/about.mp3",
                "loud" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Diphthongs & word examples",
            sound = "/əʊ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "go" to "lessons/lesson1/about.mp3",
                "home" to "lessons/lesson1/about.mp3",
                "throw" to "lessons/lesson1/about.mp3",
                "show" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɪə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "here" to "lessons/lesson1/about.mp3",
                "near" to "lessons/lesson1/about.mp3",
                "clear" to "lessons/lesson1/about.mp3",
                "cheer" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of Diphthongs & word examples",
            sound = "/eə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "ait" to "lessons/lesson1/about.mp3",
                "care" to "lessons/lesson1/about.mp3",
                "chair" to "lessons/lesson1/about.mp3",
                "fair" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ʊə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "pure" to "lessons/lesson1/about.mp3",
                "sure" to "lessons/lesson1/about.mp3",
                "tour" to "lessons/lesson1/about.mp3",
                "endure" to "lessons/lesson1/about.mp3",
            ),
        ),
    )
}
