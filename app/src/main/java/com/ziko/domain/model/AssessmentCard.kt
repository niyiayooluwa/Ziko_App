package com.ziko.domain.model
/**
 * File: AssessmentCard.kt
 * Layer: data (remote)
 * Description:
 * Represents a structured data model used to render assessment-related cards
 * on the UI layer, specifically translated from raw remote response data.
 *
 * The file includes both the `AssessmentCardInfo` model and a transformation logic
 * to convert API response objects (`AssessmentStatsItem`) into this local format.
 */

import com.ziko.data.remote.model.AssessmentStatsItem

/**
 * Data class representing a structured summary of a lesson's assessment statistics.
 *
 * This class is used to present user-specific assessment data such as highest score
 * and accuracy percentage for a particular lesson in the UI (typically in cards).
 *
 * @property title The display title of the assessment (e.g., "Monophthongs")
 * @property id Internal lesson identifier used for routing or content access (e.g., "lesson1")
 * @property highestScore The user's highest score for this lesson (nullable if not attempted)
 * @property accuracy The user's pronunciation accuracy for the lesson (nullable if unrecorded)
 */
data class AssessmentCardInfo(
    val title: String,
    val id: String,
    val highestScore: Int? = null,
    val accuracy: Int? = null
) {
    companion object {
        /**
         * Converts a remote [AssessmentStatsItem] into a local [AssessmentCardInfo] model.
         *
         * Performs a title-to-ID mapping to align remote titles with local lesson identifiers.
         * If the title does not match any known lesson, defaults to "lesson1".
         *
         * @param item The [AssessmentStatsItem] object received from the API
         * @return A mapped [AssessmentCardInfo] object for UI representation
         *
         * ### Example:
         * ```kotlin
         * val stats = AssessmentStatsItem("Diphthongs", 87, 93)
         * val card = AssessmentCardInfo.fromStatsItem(stats)
         * // card = AssessmentCardInfo(title="Diphthongs", id="lesson2", highestScore=87, accuracy=93)
         * ```
         *
         * @see AssessmentStatsItem
         */
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
                else -> "lesson1" // Fallback default
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
