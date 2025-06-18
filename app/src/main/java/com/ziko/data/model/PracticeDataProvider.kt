package com.ziko.data.model

import com.ziko.data.local.practice.getPractice1Content
import com.ziko.data.local.practice.getPractice2Content
import com.ziko.data.local.practice.getPractice3Content
import com.ziko.data.local.practice.getPractice4Content
import com.ziko.data.local.practice.getPractice5Content
import com.ziko.data.local.practice.getPractice6Content
import com.ziko.data.local.practice.getPractice7Content
import com.ziko.data.local.practice.getPractice8Content
import com.ziko.ui.model.PracticeScreenContent

object PracticeDataProvider {
    fun getPracticeContent(lessonId: String): List<PracticeScreenContent> {
        return when (lessonId) {
            "lesson1" -> getPractice1Content()
            "lesson2" -> getPractice2Content()
            "lesson3" -> getPractice3Content()
            "lesson4" -> getPractice4Content()
            "lesson5" -> getPractice5Content()
            "lesson6" -> getPractice6Content()
            "lesson7" -> getPractice7Content()
            "lesson8" -> getPractice8Content()
            else -> getPractice1Content()
        }
    }
}
