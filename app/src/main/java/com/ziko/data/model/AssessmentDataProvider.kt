package com.ziko.data.model

import com.ziko.data.assessment.getAssessment1Content
/*{import com.[lziko.data.assessment.getAssessment2Content
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
            /*{"assessment2" -> getAssessment2Content()
            "assessment3" -> getAssessment3Content()
            "assessment4" -> getAssessment4Content()
            "assessment5" -> getAssessment5Content()
            "assessment6" -> getAssessment6Content()
            "assessment7" -> getAssessment7Content()
            "assessment8" -> getAssessment8Content()}*/
            else -> emptyList()
        }
    }
}
