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
            sound = "/aɪə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "fire" to R.raw.cut_cart,
                "liar" to R.raw.cut_cart,
                "tired" to R.raw.cut_cart,
                "hire" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Triphthongs & word examples",
            sound = "/aʊə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "our" to R.raw.cut_cart,
                "hour" to R.raw.cut_cart,
                "power" to R.raw.cut_cart,
                "flower" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Triphthongs & word examples",
            sound = "/ɔɪə/" to R.raw.short_or,
            subText = "Example words:",
            options = listOf(
                "lawyer" to R.raw.cut_cart,
                "royal" to R.raw.cut_cart,
                "loyal" to R.raw.cut_cart,
                "employer" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Triphthongs & word examples",
            sound = "/eɪə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "player" to R.raw.cut_cart,
                "layer" to R.raw.cut_cart,
                "mayor" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Triphthongs & word examples",
            sound = "/əʊə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "lower" to R.raw.cut_cart,
                "mower" to R.raw.cut_cart,
                "slower" to R.raw.cut_cart,
            ),
        ),
    )
}
