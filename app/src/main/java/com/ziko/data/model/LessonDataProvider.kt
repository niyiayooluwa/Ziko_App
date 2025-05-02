package com.ziko.data.model

import com.ziko.data.lessons.getLesson1Content
import com.ziko.data.lessons.getLesson1Info
import com.ziko.data.lessons.getLesson2Content
import com.ziko.data.lessons.getLesson2Info
import com.ziko.data.lessons.getLesson3Content
import com.ziko.data.lessons.getLesson3Info
import com.ziko.data.lessons.getLesson4Content
import com.ziko.data.lessons.getLesson4Info
import com.ziko.data.lessons.getLesson5Content
import com.ziko.data.lessons.getLesson5Info
import com.ziko.data.lessons.getLesson6Content
import com.ziko.data.lessons.getLesson6Info
import com.ziko.data.lessons.getLesson7Content
import com.ziko.data.lessons.getLesson7Info
import com.ziko.data.lessons.getLesson8Content
import com.ziko.data.lessons.getLesson8Info
import com.ziko.ui.model.LessonScreenContent

object LessonDataProvider {
    fun getLessonContent(lessonId: String): List<LessonScreenContent> {
        return when (lessonId) {
            "lesson1" -> getLesson1Content()
            "lesson2" -> getLesson2Content()
            "lesson3" -> getLesson3Content()
            "lesson4" -> getLesson4Content()
            "lesson5" -> getLesson5Content()
            "lesson6" -> getLesson6Content()
            "lesson7" -> getLesson7Content()
            "lesson8" -> getLesson8Content()
            else -> emptyList()
        }
    }

    fun getLessonInfo(lessonId: String): LessonCard? {
        return when (lessonId) {
            "lesson1" -> getLesson1Info()
            "lesson2" -> getLesson2Info()
            "lesson3" -> getLesson3Info()
            "lesson4" -> getLesson4Info()
            "lesson5" -> getLesson5Info()
            "lesson6" -> getLesson6Info()
            "lesson7" -> getLesson7Info()
            "lesson8" -> getLesson8Info()
            else -> null
        }
    }
}
