package com.ziko.data.lessons

import androidx.compose.ui.graphics.Color
import com.ziko.data.model.LessonCard
import com.ziko.ui.model.LessonScreenContent

fun getLesson1Info(): LessonCard {
    return LessonCard(
        title = "Monophthongs",
        description = "Master the basics of audio comprehension.",
        id = "lesson1",
        color = Color(0xFF410FA3)
    )
}

fun getLesson1Content(): List<LessonScreenContent> {
    return listOf(
        LessonScreenContent(
            id = 1,
            mainText = "Short vowel /ɪ/ & word examples",
            sound = "/ɪ/" to "lessons/lesson1/vowel_i_short.mp3",
            subText = "Example words:",
            options = listOf(
                "bit" to "lessons/lesson1/bit.mp3",
                "sit" to "lessons/lesson1/sit.mp3",
                "fit" to "lessons/lesson1/fit.mp3",
                "ship" to "lessons/lesson1/ship.mp3",
            ),
        ),

        LessonScreenContent(
            id = 2,
            mainText = "Short vowel /e/ & word examples",
            sound = "/e/" to "lessons/lesson1/vowel_e_short.mp3",
            subText = "Example words:",
            options = listOf(
                "bet" to "lessons/lesson1/bet.mp3",
                "set" to "lessons/lesson1/set.mp3",
                "let" to "lessons/lesson1/let.mp3",
                "pen" to "lessons/lesson1/pen.mp3",
            ),
        ),

        LessonScreenContent(
            id = 3,
            mainText = "Short vowel /æ/ & word examples",
            sound = "/æ/" to "lessons/lesson1/vowel_a_short.mp3",
            subText = "Example words:",
            options = listOf(
                "cat" to "lessons/lesson1/cat.mp3",
                "mat" to "lessons/lesson1/mat.mp3",
                "fat" to "lessons/lesson1/fat.mp3",
                "tap" to "lessons/lesson1/tap.mp3",
            ),
        ),

        LessonScreenContent(
            id = 4,
            mainText = "Short vowel /ʌ/ & word examples",
            sound = "/ʌ/" to "lessons/lesson1/vowel_uh_short.mp3",
            subText = "Example words:",
            options = listOf(
                "cut" to "lessons/lesson1/cut.mp3",
                "cup" to "lessons/lesson1/cup.mp3",
                "hut" to "lessons/lesson1/hut.mp3",
                "luck" to "lessons/lesson1/luck.mp3",
            ),
        ),

        LessonScreenContent(
            id = 5,
            mainText = "Short vowel /ɒ/ & word examples",
            sound = "/ɒ/" to "lessons/lesson1/vowel_o_short.mp3",
            subText = "Example words:",
            options = listOf(
                "hot" to "lessons/lesson1/hot.mp3",
                "pot" to "lessons/lesson1/pot.mp3",
                "lot" to "lessons/lesson1/lot.mp3",
                "dog" to "lessons/lesson1/dog.mp3",
            ),
        ),

        LessonScreenContent(
            id = 6,
            mainText = "Short vowel /ʊ/ & word examples",
            sound = "/ʊ/" to "lessons/lesson1/vowel_u_short.mp3",
            subText = "Example words:",
            options = listOf(
                "put" to "lessons/lesson1/put.mp3",
                "look" to "lessons/lesson1/look.mp3",
                "book" to "lessons/lesson1/book.mp3",
                "foot" to "lessons/lesson1/foot.mp3",
            ),
        ),

        LessonScreenContent(
            id = 7,
            mainText = "Short vowel /ə/ & word examples",
            sound = "/ə/" to "lessons/lesson1/vowel_schwa.mp3",
            subText = "Example words:",
            options = listOf(
                "sofa" to "lessons/lesson1/sofa.mp3",
                "about" to "lessons/lesson1/about.mp3",
                "banana" to "lessons/lesson1/banana.mp3",
                "cinema" to "lessons/lesson1/cinema.mp3",
            ),
        ),

        LessonScreenContent(
            id = 8,
            mainText = "Long vowel /iː/ & word examples",
            sound = "/iː/" to "lessons/lesson1/vowel_i_long.mp3",
            subText = "Example words:",
            options = listOf(
                "bead" to "lessons/lesson1/bead.mp3",
                "seed" to "lessons/lesson1/seed.mp3",
                "neat" to "lessons/lesson1/neat.mp3",
                "feet" to "lessons/lesson1/feet.mp3",
            ),
        ),

        LessonScreenContent(
            id = 9,
            mainText = "Long vowel /ɑː/ & word examples",
            sound = "/ɑː/" to "lessons/lesson1/vowel_ah_long.mp3",
            subText = "Example words:",
            options = listOf(
                "car" to "lessons/lesson1/car.mp3",
                "father" to "lessons/lesson1/father.mp3",
                "star" to "lessons/lesson1/star.mp3",
                "park" to "lessons/lesson1/park.mp3",
            ),
        ),

        LessonScreenContent(
            id = 10,
            mainText = "Long vowel /ɔː/ & word examples",
            sound = "/ɔː/" to "lessons/lesson1/vowel_aw_long.mp3",
            subText = "Example words:",
            options = listOf(
                "door" to "lessons/lesson1/door.mp3",
                "more" to "lessons/lesson1/more.mp3",
                "law" to "lessons/lesson1/law.mp3",
                "talk" to "lessons/lesson1/talk.mp3",
            ),
        ),

        LessonScreenContent(
            id = 11,
            mainText = "Long vowel /uː/ & word examples",
            sound = "/uː/" to "lessons/lesson1/vowel_u_long.mp3",
            subText = "Example words:",
            options = listOf(
                "food" to "lessons/lesson1/food.mp3",
                "mood" to "lessons/lesson1/mood.mp3",
                "blue" to "lessons/lesson1/blue.mp3",
                "too" to "lessons/lesson1/too.mp3",
            ),
        ),

        LessonScreenContent(
            id = 12,
            mainText = "Long vowel /ɜː/ & word examples",
            sound = "/ɜː/" to "lessons/lesson1/vowel_er_long.mp3",
            subText = "Example words:",
            options = listOf(
                "bird" to "lessons/lesson1/bird.mp3",
                "word" to "lessons/lesson1/word.mp3",
                "third" to "lessons/lesson1/third.mp3",
                "nurse" to "lessons/lesson1/nurse.mp3",
            ),
        ),
    )
}
