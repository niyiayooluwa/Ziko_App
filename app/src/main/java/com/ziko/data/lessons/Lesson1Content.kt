package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson1Info(): LessonCard {
    return LessonCard(
        title = "Monophthongs",
        description = "Master the basics of audio comprehension.",
        id = "lesson1",
        color = Color(0xFF410FA3)
    )
}

fun getLesson1Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Short vowel /ɪ/ & word examples",
            sound = "/ɪ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "bit" to R.raw.cut_cart,
                "sit" to R.raw.cut_cart,
                "fit" to R.raw.cut_cart,
                "ship" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Short vowel /e/ & word examples",
            sound = "/e/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "bet" to R.raw.cut_cart,
                "set" to R.raw.cut_cart,
                "let" to R.raw.cut_cart,
                "pen" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Short vowel /æ/ & word examples",
            sound = "/æ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "cat" to R.raw.cut_cart,
                "mat" to R.raw.cut_cart,
                "fat" to R.raw.cut_cart,
                "tap" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Short vowel /ʌ/ & word examples",
            sound = "/ʌ/" to R.raw.short_or,
            subText = "Example words:",
            options = listOf(
                "cut" to R.raw.cut_cart,
                "cup" to R.raw.cut_cart,
                "hut" to R.raw.cut_cart,
                "luck" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Short vowel /ɒ/ & word examples",
            sound = "/ɒ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "hot" to R.raw.cut_cart,
                "pot" to R.raw.cut_cart,
                "lot" to R.raw.cut_cart,
                "dog" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Short vowel /ʊ/ & word examples",
            sound = "/ʊ/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "put" to R.raw.cut_cart,
                "look" to R.raw.cut_cart,
                "book" to R.raw.cut_cart,
                "foot" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Short vowel /ə/ & word examples",
            sound = "/ə/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "sofa" to R.raw.cut_cart,
                "about" to R.raw.cut_cart,
                "banana" to R.raw.cut_cart,
                "cinema" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Long vowel /iː/ & word examples",
            sound = "/iː/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "bead" to R.raw.cut_cart,
                "seed" to R.raw.cut_cart,
                "neat" to R.raw.cut_cart,
                "feet" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 9,
            mainText = "Long vowel /ɑː/ & word examples",
            sound = "/ɑː/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "car" to R.raw.cut_cart,
                "father" to R.raw.cut_cart,
                "star" to R.raw.cut_cart,
                "park" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 10,
            mainText = "Long vowel /ɔː/ & word examples",
            sound = "/ɔː/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "door" to R.raw.cut_cart,
                "more" to R.raw.cut_cart,
                "law" to R.raw.cut_cart,
                "talk" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 11,
            mainText = "Long vowel /uː/ & word examples",
            sound = "/uː/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "food" to R.raw.cut_cart,
                "mood" to R.raw.cut_cart,
                "blue" to R.raw.cut_cart,
                "too" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 12,
            mainText = "Long vowel /ɜː/ & word examples",
            sound = "/ɜː/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "bird" to R.raw.cut_cart,
                "word" to R.raw.cut_cart,
                "third" to R.raw.cut_cart,
                "nurse" to R.raw.cut_cart,
            ),
        ),
    )
}
