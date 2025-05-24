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
            sound = "/p/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "pat" to "lessons/lesson1/about.mp3",
                "pan" to "lessons/lesson1/about.mp3",
                "cap" to "lessons/lesson1/about.mp3",
                "top" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/t/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "ten" to "lessons/lesson1/about.mp3",
                "top" to "lessons/lesson1/about.mp3",
                "cat" to "lessons/lesson1/about.mp3",
                "water" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/k/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "cat" to "lessons/lesson1/about.mp3",
                "kick" to "lessons/lesson1/about.mp3",
                "park" to "lessons/lesson1/about.mp3",
                "black" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/f/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "fish" to "lessons/lesson1/about.mp3",
                "phone" to "lessons/lesson1/about.mp3",
                "coffee" to "lessons/lesson1/about.mp3",
                "laugh" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/θ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "think" to "lessons/lesson1/about.mp3",
                "three" to "lessons/lesson1/about.mp3",
                "tooth" to "lessons/lesson1/about.mp3",
                "birthday" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/s/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "sit" to "lessons/lesson1/about.mp3",
                "see" to "lessons/lesson1/about.mp3",
                "bus" to "lessons/lesson1/about.mp3",
                "nice" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/ʃ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "shoe" to "lessons/lesson1/about.mp3",
                "shop" to "lessons/lesson1/about.mp3",
                "wash" to "lessons/lesson1/about.mp3",
                "fish" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/tʃ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "chop" to "lessons/lesson1/about.mp3",
                "cheese" to "lessons/lesson1/about.mp3",
                "teacher" to "lessons/lesson1/about.mp3",
                "church" to "lessons/lesson1/about.mp3",
            ),
        ),
    )
}
