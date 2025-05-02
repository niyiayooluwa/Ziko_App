package com.ziko.ui.model

data class LessonScreenContent(
    val id: Int,
    val mainText: String,
    val sound: Pair<String, Int>,
    val subText: String,
    val options: List<Pair<String, Int>>
)
