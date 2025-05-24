package com.ziko.ui.model

data class LessonIntroContent(
    val definitionTextOne: String,
    val definitionTextTwo: String,
    val definitionAudio: String,
    val points: List<String?>? = null
)
