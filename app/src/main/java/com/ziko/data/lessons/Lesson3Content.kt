// lesson3Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson3Info(): LessonCard {
    return LessonCard(
        title = "Triphthongs",
        description = "Learn complex sounds formed by combining three vowel sounds in one syllable.",
        id = "lesson3",
        color = Color(0xFF410FA3)
    )
}

fun getLesson3Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of Triphthongs & word examples",
            sound = "/aɪə/" to "lessons/lesson3/tri_i.mp3",
            subText = "Example words:",
            options = listOf(
                "fire" to "lessons/lesson3/fire.mp3",
                "liar" to "lessons/lesson3/liar.mp3",
                "tired" to "lessons/lesson3/tired.mp3",
                "hire" to "lessons/lesson3/hire.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Triphthongs & word examples",
            sound = "/aʊə/" to "lessons/lesson3/tri_awa.mp3",
            subText = "Example words:",
            options = listOf(
                "our" to "lessons/lesson3/our.mp3",
                "hour" to "lessons/lesson3/hour.mp3",
                "power" to "lessons/lesson3/power.mp3",
                "flower" to "lessons/lesson3/flower.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Triphthongs & word examples",
            sound = "/ɔɪə/" to "lessons/lesson3/tri_oy.mp3",
            subText = "Example words:",
            options = listOf(
                "lawyer" to "lessons/lesson3/lawyer.mp3",
                "royal" to "lessons/lesson3/royal.mp3",
                "loyal" to "lessons/lesson3/loyal.mp3",
                "employer" to "lessons/lesson3/employer.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Triphthongs & word examples",
            sound = "/eɪə/" to "lessons/lesson3/tri_ay.mp3",
            subText = "Example words:",
            options = listOf(
                "player" to "lessons/lesson3/player.mp3",
                "layer" to "lessons/lesson3/layer.mp3",
                "mayor" to "lessons/lesson3/mayor.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Triphthongs & word examples",
            sound = "/əʊə/" to "lessons/lesson3/tri_ow.mp3",
            subText = "Example words:",
            options = listOf(
                "lower" to "lessons/lesson3/lower.mp3",
                "mower" to "lessons/lesson3/mower.mp3",
                "slower" to "lessons/lesson3/slower.mp3",
            ),
        ),
    )
}
