// lesson5Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson5Info(): LessonCard {
    return LessonCard(
        title = "Voiceless Consonants",
        description = "Master the basics of audio comprehension.",
        id = "lesson5",
        color = Color(0xFF410FA3)
    )
}

fun getLesson5Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/p/" to "lessons/lesson5/consonant_p.mp3",
            subText = "Example words:",
            options = listOf(
                "pat" to "lessons/lesson5/pat.mp3",
                "pan" to "lessons/lesson5/pan.mp3",
                "cap" to "lessons/lesson5/cap.mp3",
                "top" to "lessons/lesson5/top.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/t/" to "lessons/lesson5/consonant_t.mp3",
            subText = "Example words:",
            options = listOf(
                "ten" to "lessons/lesson5/ten.mp3",
                "top" to "lessons/lesson5/top.mp3",
                "cat" to "lessons/lesson5/cat.mp3",
                "water" to "lessons/lesson5/water.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/k/" to "lessons/lesson5/consonant_k.mp3",
            subText = "Example words:",
            options = listOf(
                "cat" to "lessons/lesson5/cat.mp3",
                "kick" to "lessons/lesson5/kick.mp3",
                "park" to "lessons/lesson5/park.mp3",
                "black" to "lessons/lesson5/black.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/f/" to "lessons/lesson5/consonant_f.mp3",
            subText = "Example words:",
            options = listOf(
                "fish" to "lessons/lesson5/fish.mp3",
                "phone" to "lessons/lesson5/phone.mp3",
                "coffee" to "lessons/lesson5/coffee.mp3",
                "laugh" to "lessons/lesson5/laugh.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/θ/" to "lessons/lesson5/consonant_th.mp3",
            subText = "Example words:",
            options = listOf(
                "think" to "lessons/lesson5/think.mp3",
                "three" to "lessons/lesson5/three.mp3",
                "tooth" to "lessons/lesson5/tooth.mp3",
                "birthday" to "lessons/lesson5/birthday.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/s/" to "lessons/lesson5/consonant_s.mp3",
            subText = "Example words:",
            options = listOf(
                "sit" to "lessons/lesson5/sit.mp3",
                "see" to "lessons/lesson5/see.mp3",
                "bus" to "lessons/lesson5/bus.mp3",
                "nice" to "lessons/lesson5/nice.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/ʃ/" to "lessons/lesson5/consonant_sh.mp3",
            subText = "Example words:",
            options = listOf(
                "shoe" to "lessons/lesson5/shoe.mp3",
                "shop" to "lessons/lesson5/shop.mp3",
                "wash" to "lessons/lesson5/wash.mp3",
                "fish" to "lessons/lesson5/fish.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiceless consonants & word examples",
            sound = "/tʃ/" to "lessons/lesson5/consonant_ch.mp3",
            subText = "Example words:",
            options = listOf(
                "chop" to "lessons/lesson5/chop.mp3",
                "cheese" to "lessons/lesson5/cheese.mp3",
                "teacher" to "lessons/lesson5/teacher.mp3",
                "church" to "lessons/lesson5/church.mp3",
            ),
        ),
    )
}
