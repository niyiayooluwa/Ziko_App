// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

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
            sound = "/eɪ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "day" to R.raw.cut_cart,
                "pay" to R.raw.cut_cart,
                "say" to R.raw.cut_cart,
                "weight" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aɪ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "high" to R.raw.cut_cart,
                "my" to R.raw.cut_cart,
                "right" to R.raw.cut_cart,
                "kite" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɔɪ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "boy" to R.raw.cut_cart,
                "joy" to R.raw.cut_cart,
                "toy" to R.raw.cut_cart,
                "noise" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aʊ/" to R.raw.short_or,
            subText = "Example words:",
            options = listOf(
                "now" to R.raw.cut_cart,
                "cow" to R.raw.cut_cart,
                "house" to R.raw.cut_cart,
                "loud" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Diphthongs & word examples",
            sound = "/əʊ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "go" to R.raw.cut_cart,
                "home" to R.raw.cut_cart,
                "throw" to R.raw.cut_cart,
                "show" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɪə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "here" to R.raw.cut_cart,
                "near" to R.raw.cut_cart,
                "clear" to R.raw.cut_cart,
                "cheer" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of Diphthongs & word examples",
            sound = "/eə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "ait" to R.raw.cut_cart,
                "care" to R.raw.cut_cart,
                "chair" to R.raw.cut_cart,
                "fair" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ʊə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "pure" to R.raw.cut_cart,
                "sure" to R.raw.cut_cart,
                "tour" to R.raw.cut_cart,
                "endure" to R.raw.cut_cart,
            ),
        ),
    )
}
