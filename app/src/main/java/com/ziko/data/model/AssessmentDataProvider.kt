package com.ziko.data.model

import com.ziko.data.assessment.getAssessment1Content
import com.ziko.data.assessment.getAssessment2Content
import com.ziko.data.assessment.getAssessment3Content
import com.ziko.data.assessment.getAssessment4Content
import com.ziko.data.assessment.getAssessment5Content
import com.ziko.data.assessment.getAssessment6Content
import com.ziko.data.assessment.getAssessment7Content
import com.ziko.data.assessment.getAssessment8Content
/*{
import com.ziko.data.assessment.getAssessment3Content
import com.ziko.data.assessment.getAssessment4Content
import com.ziko.data.assessment.getAssessment5Content
import com.ziko.data.assessment.getAssessment6Content
import com.ziko.data.assessment.getAssessment7Content
import com.ziko.data.assessment.getAssessment8Content}*/
import com.ziko.ui.model.AssessmentScreenContent

object AssessmentDataProvider {
    fun getAssessmentContent(lessonId: String): List<AssessmentScreenContent> {
        return when (lessonId) {
            "lesson1" -> getAssessment1Content()
            "lesson2" -> getAssessment2Content()
            "lesson3" -> getAssessment3Content()
            "lesson4" -> getAssessment4Content()
            "lesson5" -> getAssessment5Content()
            "lesson6" -> getAssessment6Content()
            "lesson7" -> getAssessment7Content()
            "lesson8" -> getAssessment8Content()
            else -> emptyList()
        }
    }
}
