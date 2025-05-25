// lesson7Content.kt
package com.ziko.data.lessons

import androidx.compose.ui.graphics.Color
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent

fun getLesson7Info(): LessonCard {
    return LessonCard(
        title = "Stress",
        description = "Master the basics of audio comprehension.",
        id = "lesson7",
        color = Color(0xFF410FA3)
    )
}

fun getLesson7Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Examples of word stress",
            options = listOf(
                "PHOto" to "lessons/lesson7/photo.mp3",
                "toMAto" to "lessons/lesson7/tomato.mp3",
                "aBOVE" to "lessons/lesson7/above.mp3",
                "beLIVE" to "lessons/lesson7/believe.mp3",
                "deCIDE" to "lessons/lesson7/decide.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Example of sentence stress",
            subText = "Example sentence:",
            options = listOf(
                "I LOVE apples" to "lessons/lesson7/i_love_apples.mp3",
                "He BOUGHT a NEW car" to "lessons/lesson7/he_bought_a.mp3",
            ),
        ),
    )
}
