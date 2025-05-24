package com.ziko.ui.model

data class LessonScreenContent(
    val id: Int,
    val mainText: String,
    val boldText: String? = null,
    val sound: Pair<String, String>? = null,
    val subText: String? = null,
    val options: List<Pair<String, String>>
)
