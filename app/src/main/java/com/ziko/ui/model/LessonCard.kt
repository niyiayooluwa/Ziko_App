package com.ziko.ui.model

import androidx.compose.ui.graphics.Color

/**
 * Data class representing the visual and metadata configuration of a lesson card.
 *
 * Each lesson card contains descriptive text and a color accent for theming purposes.
 * The `color` parameter directly influences the composable's UI appearance, meaning this model
 * carries some presentation-layer responsibility.
 *
 * @property title The display title of the lesson (e.g., "Lesson 1")
 * @property description A short summary or hint of what the lesson contains
 * @property id Unique identifier for the lesson (used for routing or content mapping, e.g., "lesson1")
 * @property color A Jetpack Compose `Color` used to tint the lesson card for visual distinction
 *
 * ### Usage Example:
 * ```kotlin
 * LessonCard(
 *   title = "Lesson 1",
 *   description = "Introduction to minimal pairs",
 *   id = "lesson1",
 *   color = Color(0xFF42A5F5)
 * )
 * ```
 */
data class LessonCard(
    val title: String,
    val description: String,
    val id: String,
    val color: Color
)
