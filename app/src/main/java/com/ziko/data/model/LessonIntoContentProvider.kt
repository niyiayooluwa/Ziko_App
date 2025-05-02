package com.ziko.data.model

import com.ziko.R
import com.ziko.ui.model.LessonIntroContent

object LessonIntroContentProvider {
    fun getIntroContent(lessonId: String): LessonIntroContent =
        when (lessonId) {
            "lesson1" -> LessonIntroContent(
                definitionTextOne = "Monophthongs",
                definitionTextTwo = " are single pure vowel sounds that do not change in quality during articulation.",
                definitionAudio = R.raw.cut_cart,
                points = listOf(
                    "Point one about the vowel",
                    "Point two explaining usage",
                    // up to six entries...
                )
            )
            else -> LessonIntroContent(
                definitionTextOne = "Definition unavailable",
                definitionTextTwo = "Definition unavailable",
                definitionAudio = R.raw.cut_cart,
                points = emptyList()
            )
        }
}
