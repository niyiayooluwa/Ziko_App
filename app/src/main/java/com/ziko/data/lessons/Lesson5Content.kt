// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson5Info(): LessonCard {
    return LessonCard(
        title = "Voiceless Consonants",
        description = "Master the basics of audio comprehension.",
        id = "lesson5",
        color = Color(0xFF410FA3)
    )
}

fun getLesson5Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/p/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "pat" to R.raw.cut_cart,
                "pan" to R.raw.cut_cart,
                "cap" to R.raw.cut_cart,
                "top" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/t/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "ten" to R.raw.cut_cart,
                "top" to R.raw.cut_cart,
                "cat" to R.raw.cut_cart,
                "water" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/k/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "cat" to R.raw.cut_cart,
                "kick" to R.raw.cut_cart,
                "park" to R.raw.cut_cart,
                "black" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/f/" to R.raw.short_or,
            subText = "Example words:",
            options = listOf(
                "fish" to R.raw.cut_cart,
                "phone" to R.raw.cut_cart,
                "coffee" to R.raw.cut_cart,
                "laugh" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/θ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "think" to R.raw.cut_cart,
                "three" to R.raw.cut_cart,
                "tooth" to R.raw.cut_cart,
                "birthday" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/s/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "sit" to R.raw.cut_cart,
                "see" to R.raw.cut_cart,
                "bus" to R.raw.cut_cart,
                "nice" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/ʃ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "shoe" to R.raw.cut_cart,
                "shop" to R.raw.cut_cart,
                "wash" to R.raw.cut_cart,
                "fish" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/tʃ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "chop" to R.raw.cut_cart,
                "cheese" to R.raw.cut_cart,
                "teacher" to R.raw.cut_cart,
                "church" to R.raw.cut_cart,
            ),
        ),
    )
}
