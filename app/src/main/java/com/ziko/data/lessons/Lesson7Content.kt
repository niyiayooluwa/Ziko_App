// Lesson1Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

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
                "PHOto" to R.raw.cut_cart,
                "toMAto" to R.raw.cut_cart,
                "aBOVE" to R.raw.cut_cart,
                "beLIVE" to R.raw.cut_cart,
                "deCIDE" to R.raw.cut_cart,
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Example of sentence stress",
            subText = "Example sentence:",
            options = listOf(
                "I LOVE apples" to R.raw.cut_cart,
                "He BOUGHT a NEW car" to R.raw.cut_cart,
            ),
        ),
    )
}
