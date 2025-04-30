package com.ziko.data.model

import com.ziko.R
import com.ziko.ui.model.LessonScreenContent

// --- Sample Data Provider ---
object LessonDataProvider {
    fun getLessonContent(lessonId: String): List<LessonScreenContent> {
        return when (lessonId) {
            "lesson1" -> listOf(
                LessonScreenContent(
                    id = 1,
                    mainText = "Listen and Repeat",
                    audioResId = R.raw.cut_cart,
                    subText = "This is the first example",
                    options = listOf(
                        "Option 1" to R.raw.cut_cart,
                        "Option 2" to R.raw.cut_cart,
                        "Option 3" to R.raw.cut_cart,
                        "Option 4" to R.raw.cut_cart,
                    )
                ),
                // Add more screens...
            )
            else -> emptyList()
        }
    }
}