package com.ziko.data.model

import com.ziko.data.practice.getPractice1Content
import com.ziko.data.practice.getPractice1Info
import com.ziko.data.practice.getPractice2Content
import com.ziko.data.practice.getPractice2Info
import com.ziko.data.practice.getPractice3Content
import com.ziko.data.practice.getPractice3Info
import com.ziko.data.practice.getPractice4Content
import com.ziko.data.practice.getPractice4Info
import com.ziko.data.practice.getPractice5Content
import com.ziko.data.practice.getPractice5Info
import com.ziko.data.practice.getPractice6Content
import com.ziko.data.practice.getPractice6Info
import com.ziko.data.practice.getPractice7Content
import com.ziko.data.practice.getPractice7Info
import com.ziko.data.practice.getPractice8Content
import com.ziko.data.practice.getPractice8Info
import com.ziko.ui.model.PracticeScreenContent

object PracticeDataProvider {
    fun getPracticeContent(practiceId: String): List<PracticeScreenContent> {
        return when (practiceId) {
            "practice1" -> getPractice1Content()
            "practice2" -> getPractice2Content()
            "practice3" -> getPractice3Content()
            "practice4" -> getPractice4Content()
            "practice5" -> getPractice5Content()
            "practice6" -> getPractice6Content()
            "practice7" -> getPractice7Content()
            "practice8" -> getPractice8Content()
            else -> emptyList()
        }
    }

    fun getPracticeInfo(practiceId: String): PracticeCard? {
        return when (practiceId) {
            "practice1" -> getPractice1Info()
            "practice2" -> getPractice2Info()
            "practice3" -> getPractice3Info()
            "practice4" -> getPractice4Info()
            "practice5" -> getPractice5Info()
            "practice6" -> getPractice6Info()
            "practice7" -> getPractice7Info()
            "practice8" -> getPractice8Info()
            else -> null
        }
    }
}
