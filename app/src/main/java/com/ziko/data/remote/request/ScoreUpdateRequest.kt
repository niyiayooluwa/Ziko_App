package com.ziko.data.remote.request

/**
 * File: ScoreUpdateRequest.kt
 * Layer: data (remote)
 * Description:
 * This file defines a simple data transfer object (DTO) used for updating a user's score
 * during or after an assessment. It is intended to be serialized into a request body
 * and sent to the backend server via an API call.

 * Represents a request payload to update a user's score for a specific assessment.
 *
 * This data class is used during API interactions where the frontend sends a
 * newly calculated score to be recorded or processed by the server.
 *
 * @property score The integer value of the user's latest score for an assessment
 *
 * ### Example:
 * ```kotlin
 * val request = ScoreUpdateRequest(score = 85)
 * apiService.updateScore(lessonId = "lesson1", request)
 * ```
 */
data class ScoreUpdateRequest(
    val score: Int
)
