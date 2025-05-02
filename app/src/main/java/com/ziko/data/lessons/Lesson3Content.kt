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
            mainText = "Listen and Repeat",
            sound = "/I/" to R.raw.cut_cart,
            subText = "Example words:",
            options = listOf(
                "Option 1" to R.raw.cut_cart,
                "Option 2" to R.raw.cut_cart,
                "Option 3" to R.raw.cut_cart,
                "Option 4" to R.raw.cut_cart,
            ),
        )
    )
}
