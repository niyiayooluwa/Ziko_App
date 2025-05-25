// lesson2Content.kt
package com.ziko.data.lessons

import androidx.compose.ui.graphics.Color
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent

fun getLesson2Info(): LessonCard {
    return LessonCard(
        title = "Diphthongs",
        description = "Master gliding vowel sounds like /al/, /el/, and /əʊ/",
        id = "lesson2",
        color = Color(0xFF410FA3)
    )
}

fun getLesson2Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of Diphthongs & word examples",
            sound = "/eɪ/" to "lessons/lesson2/diph_ay.mp3",
            subText = "Example words:",
            options = listOf(
                "day" to "lessons/lesson2/day.mp3",
                "pay" to "lessons/lesson2/pay.mp3",
                "say" to "lessons/lesson2/say.mp3",
                "weight" to "lessons/lesson2/weight.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aɪ/" to "lessons/lesson2/diph_i.mp3",
            subText = "Example words:",
            options = listOf(
                "high" to "lessons/lesson2/high.mp3",
                "my" to "lessons/lesson2/my.mp3",
                "right" to "lessons/lesson2/right.mp3",
                "kite" to "lessons/lesson2/kite.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɔɪ/" to "lessons/lesson2/diph_oy.mp3",
            subText = "Example words:",
            options = listOf(
                "boy" to "lessons/lesson2/boy.mp3",
                "joy" to "lessons/lesson2/joy.mp3",
                "toy" to "lessons/lesson2/toy.mp3",
                "noise" to "lessons/lesson2/noise.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of Diphthongs & word examples",
            sound = "/aʊ/" to "lessons/lesson2/diph_ow.mp3",
            subText = "Example words:",
            options = listOf(
                "now" to "lessons/lesson2/now.mp3",
                "cow" to "lessons/lesson2/cow.mp3",
                "house" to "lessons/lesson2/house.mp3",
                "loud" to "lessons/lesson2/loud.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of Diphthongs & word examples",
            sound = "/əʊ/" to "lessons/lesson2/diph_o.mp3",
            subText = "Example words:",
            options = listOf(
                "go" to "lessons/lesson2/go.mp3",
                "home" to "lessons/lesson2/home.mp3",
                "throw" to "lessons/lesson2/throw.mp3",
                "show" to "lessons/lesson2/show.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ɪə/" to "lessons/lesson2/diph_ear.mp3",
            subText = "Example words:",
            options = listOf(
                "here" to "lessons/lesson2/here.mp3",
                "near" to "lessons/lesson2/near.mp3",
                "clear" to "lessons/lesson2/clear.mp3",
                "cheer" to "lessons/lesson2/cheer.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of Diphthongs & word examples",
            sound = "/eə/" to "lessons/lesson2/diph_air.mp3",
            subText = "Example words:",
            options = listOf(
                "air" to "lessons/lesson2/air.mp3",
                "care" to "lessons/lesson2/care.mp3",
                "chair" to "lessons/lesson2/chair.mp3",
                "fair" to "lessons/lesson2/fair.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of Diphthongs & word examples",
            sound = "/ʊə/" to "lessons/lesson2/diph_oor.mp3",
            subText = "Example words:",
            options = listOf(
                "pure" to "lessons/lesson2/pure.mp3",
                "sure" to "lessons/lesson2/sure.mp3",
                "tour" to "lessons/lesson2/tour.mp3",
                "endure" to "lessons/lesson2/endure.mp3",
            ),
        ),
    )
}
