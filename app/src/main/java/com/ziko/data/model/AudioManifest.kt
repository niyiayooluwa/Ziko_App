package com.ziko.data.model

enum class Section { LESSON, PRACTICE, ASSESSMENT }

data class LessonAudioManifest(
    val lesson: List<String> = emptyList(),
    val practice: List<String> = emptyList(),
    val assessment: List<String> = emptyList()
)

typealias AudioManifest = Map<String, LessonAudioManifest>
