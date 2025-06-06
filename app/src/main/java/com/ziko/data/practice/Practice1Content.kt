package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice1Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "bit - beat" to "lessons/lesson1/bit_beat.mp3",
            "bit beat"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "cut - cart" to "lessons/lesson1/cut_cart.mp3",
            "cut cart"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "pot - port" to "lessons/lesson1/pot_port.mp3",
            "pot port"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence",
            "The cat sat on the mat" to "lessons/lesson1/the_cat_sat_on_the_mat.mp3",
            "The cat sat on the mat"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence",
            "She eats sweet peaches" to "lessons/lesson1/she_eats_sweet_peaches.mp3",
            "She eats sweet peaches"
        ),
        PracticeScreenContent(
            6,
            "Repeat the sentence",
            "Paul bought a warm, long coat" to "lessons/lesson1/paul_bought_a_long_warm_coat.mp3",
            "Paul bought a warm, long coat"
        ),
        PracticeScreenContent(
            7,
            "Repeat the words",
            "Tom had a small hut near the river. He loved to sit and read books. One day, he found a blue bird in the garden. The bird sang a sweet song" to "lessons/lesson1/tom_had_a_small_hut_near.mp3",
            "Tom had a small hut near the river. He loved to sit and read books. One day, he found a blue bird in the garden. The bird sang a sweet song"
        ),
    )
}
