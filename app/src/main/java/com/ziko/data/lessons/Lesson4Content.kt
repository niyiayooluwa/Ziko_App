// lesson4Content.kt
package com.ziko.data.lessons

import com.ziko.R
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent
import androidx.compose.ui.graphics.Color

fun getLesson4Info(): LessonCard {
    return LessonCard(
        title = "Voiced Consonants",
        description = "Master the basics of audio comprehension.",
        id = "lesson4",
        color = Color(0xFF410FA3)
    )
}

fun getLesson4Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Types of voiced consonants & word examples",
            sound = "/b/" to "lessons/lesson4/consonant_b.mp3",
            subText = "Example words:",
            options = listOf(
                "bat" to "lessons/lesson4/bat.mp3",
                "baby" to "lessons/lesson4/baby.mp3",
                "cab" to "lessons/lesson4/cab.mp3",
                "table" to "lessons/lesson4/table.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Types of voiced consonants & word examples",
            sound = "/d/" to "lessons/lesson4/consonant_d.mp3",
            subText = "Example words:",
            options = listOf(
                "dog" to "lessons/lesson4/dog.mp3",
                "dance" to "lessons/lesson4/dance.mp3",
                "add" to "lessons/lesson4/add.mp3",
                "bed" to "lessons/lesson4/bed.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Types of voiced consonants & word examples",
            sound = "/g/" to "lessons/lesson4/consonant_g.mp3",
            subText = "Example words:",
            options = listOf(
                "go" to "lessons/lesson4/go.mp3",
                "garden" to "lessons/lesson4/garden.mp3",
                "bag" to "lessons/lesson4/bag.mp3",
                "big" to "lessons/lesson4/big.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Types of voiced consonants & word examples",
            sound = "/v/" to "lessons/lesson4/consonant_v.mp3",
            subText = "Example words:",
            options = listOf(
                "van" to "lessons/lesson4/van.mp3",
                "very" to "lessons/lesson4/very.mp3",
                "have" to "lessons/lesson4/have.mp3",
                "love" to "lessons/lesson4/love.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ð/" to "lessons/lesson4/consonant_th.mp3",
            subText = "Example words:",
            options = listOf(
                "this" to "lessons/lesson4/this.mp3",
                "that" to "lessons/lesson4/that.mp3",
                "mother" to "lessons/lesson4/mother.mp3",
                "father" to "lessons/lesson4/father.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Types of voiced consonants & word examples",
            sound = "/z/" to "lessons/lesson4/consonant_z.mp3",
            subText = "Example words:",
            options = listOf(
                "zebra" to "lessons/lesson4/zebra.mp3",
                "zoo" to "lessons/lesson4/zoo.mp3",
                "buzz" to "lessons/lesson4/buzz.mp3",
                "nose" to "lessons/lesson4/nose.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Types of voiced consonants & word examples",
            sound = "/ʒ/" to "lessons/lesson4/consonant_sure.mp3",
            subText = "Example words:",
            options = listOf(
                "measure" to "lessons/lesson4/measure.mp3",
                "pleasure" to "lessons/lesson4/pleasure.mp3",
                "vision" to "lessons/lesson4/vision.mp3",
                "treasure" to "lessons/lesson4/treasure.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Types of voiced consonants & word examples",
            sound = "/dʒ/" to "lessons/lesson4/consonant_jg.mp3",
            subText = "Example words:",
            options = listOf(
                "jump" to "lessons/lesson4/jump.mp3",
                "juice" to "lessons/lesson4/juice.mp3",
                "judge" to "lessons/lesson4/judge.mp3",
                "giant" to "lessons/lesson4/giant.mp3",
            ),
        ),
    )
}
