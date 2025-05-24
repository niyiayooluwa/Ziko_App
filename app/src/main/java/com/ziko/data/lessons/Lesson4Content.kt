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
            sound = "/b/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "bat" to "lessons/lesson1/about.mp3",
                "baby" to "lessons/lesson1/about.mp3",
                "cab" to "lessons/lesson1/about.mp3",
                "table" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiced consonants & word examples",
            sound = "/d/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "dog" to "lessons/lesson1/about.mp3",
                "dance" to "lessons/lesson1/about.mp3",
                "add" to "lessons/lesson1/about.mp3",
                "bed" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiced consonants & word examples",
            sound = "/g/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "go" to "lessons/lesson1/about.mp3",
                "garden" to "lessons/lesson1/about.mp3",
                "bag" to "lessons/lesson1/about.mp3",
                "big" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiced consonants & word examples",
            sound = "/v/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "van" to "lessons/lesson1/about.mp3",
                "very" to "lessons/lesson1/about.mp3",
                "have" to "lessons/lesson1/about.mp3",
                "love" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ð/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "this" to "lessons/lesson1/about.mp3",
                "that" to "lessons/lesson1/about.mp3",
                "mother" to "lessons/lesson1/about.mp3",
                "father" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiced consonants & word examples",
            sound = "/z/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "zebra" to "lessons/lesson1/about.mp3",
                "zoo" to "lessons/lesson1/about.mp3",
                "buzz" to "lessons/lesson1/about.mp3",
                "nose" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ʒ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "measure" to "lessons/lesson1/about.mp3",
                "pleasure" to "lessons/lesson1/about.mp3",
                "vision" to "lessons/lesson1/about.mp3",
                "treasure" to "lessons/lesson1/about.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiced consonants & word examples",
            sound = "/dʒ/" to "lessons/lesson1/about.mp3",
            subText = "Example words:",
            options = listOf(
                "jump" to "lessons/lesson1/about.mp3",
                "juice" to "lessons/lesson1/about.mp3",
                "judge" to "lessons/lesson1/about.mp3",
                "giant" to "lessons/lesson1/about.mp3",
            ),
        ),
    )
}
