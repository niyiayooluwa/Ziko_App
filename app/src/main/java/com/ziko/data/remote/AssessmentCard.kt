package com.ziko.data.remote

import com.ziko.data.model.AssessmentStatsItem

data class AssessmentCardInfo(
    val title: String,
    val id: String,
    val highestScore: Int? = null,
    val accuracy: Int? = null
) {
    companion object {
        fun fromStatsItem(item: AssessmentStatsItem): AssessmentCardInfo {
            val id = when (item.title) {
                "Monophthongs" -> "lesson1"
                "Diphthongs" -> "lesson2"
                "Triphthongs" -> "lesson3"
                "Voiced Consonants" -> "lesson4"
                "Voiceless Consonants" -> "lesson5"
                "Intonation" -> "lesson6"
                "Stress" -> "lesson7"
                "Rhythm" -> "lesson8"
                else -> "lesson1"
            }
            return AssessmentCardInfo(
                title = item.title,
                id = id,
                highestScore = item.highestScore,
                accuracy = item.accuracy
            )
        }
    }
}



