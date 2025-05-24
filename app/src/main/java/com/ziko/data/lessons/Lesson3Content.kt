// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson3Info(): LessonCard {
    return LessonCard(
        title = "Triphthongs",
        description = "Learn complex sounds formed by combining three vowel sounds in one syllable.",
        id = "lesson3",
        color = Color(0xFF410FA3)
    )
}

fun getLesson3Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of Triphthongs & word examples",
            sound = "/aɪə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "fire" to "lessons/lesson1/about.mp3",
                "liar" to "lessons/lesson1/about.mp3",
                "tired" to "lessons/lesson1/about.mp3",
                "hire" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Triphthongs & word examples",
            sound = "/aʊə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "our" to "lessons/lesson1/about.mp3",
                "hour" to "lessons/lesson1/about.mp3",
                "power" to "lessons/lesson1/about.mp3",
                "flower" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Triphthongs & word examples",
            sound = "/ɔɪə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "lawyer" to "lessons/lesson1/about.mp3",
                "royal" to "lessons/lesson1/about.mp3",
                "loyal" to "lessons/lesson1/about.mp3",
                "employer" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Triphthongs & word examples",
            sound = "/eɪə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "player" to "lessons/lesson1/about.mp3",
                "layer" to "lessons/lesson1/about.mp3",
                "mayor" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Triphthongs & word examples",
            sound = "/əʊə/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "lower" to "lessons/lesson1/about.mp3",
                "mower" to "lessons/lesson1/about.mp3",
                "slower" to "lessons/lesson1/about.mp3",
            ),
        ),
    )
}
