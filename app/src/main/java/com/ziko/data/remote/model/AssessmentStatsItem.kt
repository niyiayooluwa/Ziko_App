package com.ziko.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * File: AssessmentStatsItem.kt
 * Layer: data (model)
 * Description:
 * This file defines the data model class `AssessmentStatsItem`, which represents
 * a single assessment's performance statistics retrieved from persistent storage
 * or a remote server (e.g., via a REST API).
 *
 * It is used primarily to represent and track user progress metrics across different lessons.
 * Fields are annotated for Gson serialization, indicating this model is used in JSON mapping.
 */

/**
 * Data class representing performance statistics for a specific lesson assessment.
 *
 * This model is designed to map to/from JSON using Gson and is typically populated
 * from a backend or local database. Each instance corresponds to one lesson.
 *
 * @property title The lesson identifier or title (e.g., "lesson1")
 * @property highestScore The user's highest recorded score for this lesson (nullable)
 * @property accuracy The user's current accuracy percentage for this lesson (nullable)
 *
 * ### Example JSON:
 * ```json
 * {
 *   "lesson": "Monophthongs",
 *   "highest_score": 85,
 *   "accuracy": 72
 * }
 * ```
 */
data class AssessmentStatsItem(
    @SerializedName("lesson")
    val title: String,

    @SerializedName("highest_score")
    val highestScore: Int?,

    @SerializedName("accuracy")
    val accuracy: Int?
)


