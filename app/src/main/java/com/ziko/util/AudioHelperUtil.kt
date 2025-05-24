package com.ziko.util

import com.ziko.data.model.AudioManifest
import com.ziko.data.model.Section

fun getAudiosForLesson(
    manifest: AudioManifest,
    lessonId: String,
    section: Section
): List<String> {
    val lesson = manifest[lessonId] ?: return emptyList()
    return when (section) {
        Section.LESSON -> lesson.lesson
        Section.PRACTICE -> lesson.practice
        Section.ASSESSMENT -> lesson.assessment
    }
}