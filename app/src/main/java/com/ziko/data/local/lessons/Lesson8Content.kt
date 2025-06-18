// lesson8Content.kt
package com.ziko.data.local.lessons

import com.ziko.ui.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson8Info(): LessonCard {
    return LessonCard(
        title = "Rhythm",
        description = "Master the basics of audio comprehension.",
        id = "lesson8",
        color = Color(0xFF410FA3)
    )
}

fun getLesson8Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Examples of rhythmic patterns",
            options = listOf(
                "The CAT ran FAST to GET the BALL" to "lessons/lesson8/the_cat_ran.mp3",
            ),
        ),

        LessonScreenContent(
            id = 1,
            mainText = "Examples of rhythmic patterns",
            options = listOf(
                "I want to go to the market " to "lessons/lesson8/i_want_to_go.mp3",
            ),
        ),
    )
}
