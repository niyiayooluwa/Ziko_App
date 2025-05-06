// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

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
                "Are you coming?" to R.raw.cut_cart,
                "Didi she call you?" to R.raw.cut_cart,
                "Are you ready?" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of intonation & sentence examples",
            boldText = "Falling intonation",
            subText = "Example sentence:",
            options = listOf(
                "I am going home" to R.raw.cut_cart,
                "I finished my work" to R.raw.cut_cart,
                "She was really happy" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of intonation & sentence examples",
            boldText = "Mixed intonation",
            subText = "Example sentence:",
            options = listOf(
                "Will, I think so" to R.raw.cut_cart,
                "Do you want pizza or pasta?" to R.raw.cut_cart,
                ),
        )
    )
}
