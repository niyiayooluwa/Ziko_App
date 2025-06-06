// lesson6Content.kt
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
                "Are you coming?" to "lessons/lesson6/are_you_coming.mp3",
                "Did she call you?" to "lessons/lesson6/did_she_call_you",
                "Are you ready?" to "lessons/lesson6/are_you_ready",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of intonation & sentence examples",
            boldText = "Falling intonation",
            subText = "Example sentence:",
            options = listOf(
                "I am going home" to "lessons/lesson6/i_am_going_home.mp3",
                "I finished my work" to "lessons/lesson6/i_finished_my_work",
                "She was really happy" to "lessons/lesson6/she_was_really_happy",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of intonation & sentence examples",
            boldText = "Mixed intonation",
            subText = "Example sentence:",
            options = listOf(
                "Well, I think so" to "lessons/lesson6/well_i_think_so.mp3",
                "Do you want pizza or pasta?" to "lessons/lesson6/do_you_want_pizza_or_pasta",
            ),
        )
    )
}
