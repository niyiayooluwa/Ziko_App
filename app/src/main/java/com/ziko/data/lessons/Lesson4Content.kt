// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson4Info(): LessonCard {
    return LessonCard(
        title = "Voiced Consonants",
        description = "Master the basics of audio comprehension.",
        id = "lesson4",
        color = Color(0xFF410FA3)
    )
}

fun getLesson4Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of voiced consonants & word examples",
            sound = "/b/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "bat" to R.raw.cut_cart,
                "baby" to R.raw.cut_cart,
                "cab" to R.raw.cut_cart,
                "table" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiced consonants & word examples",
            sound = "/d/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "dog" to R.raw.cut_cart,
                "dance" to R.raw.cut_cart,
                "add" to R.raw.cut_cart,
                "bed" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiced consonants & word examples",
            sound = "/g/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "go" to R.raw.cut_cart,
                "garden" to R.raw.cut_cart,
                "bag" to R.raw.cut_cart,
                "big" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiced consonants & word examples",
            sound = "/v/" to R.raw.short_or,
            subText = "Example words:",
            options = listOf(
                "van" to R.raw.cut_cart,
                "very" to R.raw.cut_cart,
                "have" to R.raw.cut_cart,
                "love" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ð/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "this" to R.raw.cut_cart,
                "that" to R.raw.cut_cart,
                "mother" to R.raw.cut_cart,
                "father" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiced consonants & word examples",
            sound = "/z/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "zebra" to R.raw.cut_cart,
                "zoo" to R.raw.cut_cart,
                "buzz" to R.raw.cut_cart,
                "nose" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ʒ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "measure" to R.raw.cut_cart,
                "pleasure" to R.raw.cut_cart,
                "vision" to R.raw.cut_cart,
                "treasure" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiced consonants & word examples",
            sound = "/dʒ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "jump" to R.raw.cut_cart,
                "juice" to R.raw.cut_cart,
                "judge" to R.raw.cut_cart,
                "giant" to R.raw.cut_cart,
            ),
        ),
    )
}
