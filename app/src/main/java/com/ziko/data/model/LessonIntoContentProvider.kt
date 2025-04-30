package com.ziko.data.model

import com.ziko.R
import com.ziko.ui.model.LessonIntroContent

object LessonIntroContentProvider {
    fun getIntroContent(lessonId: String): LessonIntroContent =
        when (lessonId) {
            "lesson1" -> LessonIntroContent(
                definitionText = "A monophthong is a pure vowel sound...",
                definitionAudio = R.raw.cut_cart,
                points = listOf(
                    "Point one about the vowel",
                    "Point two explaining usage",
                    // up to six entries...
                )
            )
            else -> LessonIntroContent(
                definitionText = "Definition unavailable",
                definitionAudio = R.raw.cut_cart,
                points = emptyList()
            )
        }
}
