package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice4Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "pat - bat" to "lesson/lesson4/pat_bat.mp3",
            "pat bat"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "ten - den" to "lesson/lesson4/ten_den.mp3",
            "ten den"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "sip - zip" to "lesson/lesson4/sip_zip.mp3",
            "sip zip"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence",
            "The baby loves playing with his toys" to "lesson/lesson4/the_baby_loves_playing_with.mp3",
            "The baby loves playing with his toys"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence",
            "David bought a big bag of vegetables" to "lesson/lesson4/david_bought_a_big_bag_of_vegetables.mp3",
            "David bought a big bag of vegetables"
        ),
        PracticeScreenContent(
            6,
            "Repeat the sentence",
            "The judge measured the treasure carefully" to "lesson/lesson4/the_judge_measured_the_treasure.mp3",
            "The judge measured the treasure carefully"
        ),
        PracticeScreenContent(
            7,
            "Repeat the passage",
            "Ben and Daisy visited the zoo. They saw a big zebra and a giant giraffe. The birds buzzed around as they played in the garden" to "lesson/lesson4/ben_and_daisy_visited_the_zoo.mp3",
            "Ben and Daisy visited the zoo. They saw a big zebra and a giant giraffe. The birds buzzed around as they played in the garden"
        ),
    )
}
