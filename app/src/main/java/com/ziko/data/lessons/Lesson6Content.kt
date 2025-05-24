// Lesson1Content.kt
package com.ziko.data.lessons

import androidx.compose.ui.graphics.Color
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent

fun getLesson6Info(): LessonCard {
    return LessonCard(
        title = "Intonation",
        description = "Master the basics of audio comprehension.",
        id = "lesson6",
        color = Color(0xFF410FA3)
    )
}

fun getLesson6Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of intonation & sentence examples",
            boldText = "Rising intonation",
            subText = "Example sentence:",
            options = listOf(
                "Are you coming?" to "lessons/lesson1/about.mp3",
                "Didi she call you?" to "lessons/lesson1/about.mp3",
                "Are you ready?" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of intonation & sentence examples",
            boldText = "Falling intonation",
            subText = "Example sentence:",
            options = listOf(
                "I am going home" to "lessons/lesson1/about.mp3",
                "I finished my work" to "lessons/lesson1/about.mp3",
                "She was really happy" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of intonation & sentence examples",
            boldText = "Mixed intonation",
            subText = "Example sentence:",
            options = listOf(
                "Will, I think so" to "lessons/lesson1/about.mp3",
                "Do you want pizza or pasta?" to "lessons/lesson1/about.mp3",
                ),
        )
    )
}
