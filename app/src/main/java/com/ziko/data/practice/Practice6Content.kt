package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice6Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "Really?" to "lessons/lesson6/really.mp3",
            "really"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "Okay" to "lessons/lesson6/okay.mp3",
            "okay"
        ),
        PracticeScreenContent(
            3,
            "Repeat the sentence with the correct intonation",
            "Is she your teacher?" to "lessons/lesson6/is_she_your_teacher.mp3",
            "is she your teacher"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence with the correct intonation",
            "We had a great time at the park" to "lessons/lesson6/we_had_a_great.mp3",
            "we had a great time at the park"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence with the correct intonation",
            "Are you sure you're coming tomorrow?" to "lessons/lesson6/are_you_sure.mp3",
            "are you sure you're coming tomorrow"
        ),
        PracticeScreenContent(
            6,
            "Repeat the passage with the correct intonation",
            """It was a bright morning, and Sarah was getting ready for school. She asked her mother, "Where is my backpack?" Her mother replied, "It's on the table." Sarah picked up her backpack and said, "Oh no! I forgot my homework!" Her brother laughed and asked, "Again?" She sighed and said, "Yes, I left it in my room." She ran upstairs, grabbed her homework, and hurried out the door. On the way to school, her friend Emma asked, "Did you study for the test?" Sarah shook her head and said, "Not really... I hope it's easy." When they arrived at school, the teacher greeted them warmly, "Good morning. class!" The students responded, "Good morning, ma'am!"
            """ to "lessons/lesson6/it_was_a_bright_morning.mp3",
            """
            It was a bright morning, and Sarah was getting ready for school. She asked her mother, "Where is my backpack?" Her mother replied, "It's on the table." Sarah picked up her backpack and said, "Oh no! I forgot my homework!" Her brother laughed and asked, "Again?" She sighed and said, "Yes, I left it in my room." She ran upstairs, grabbed her homework, and hurried out the door. On the way to school, her friend Emma asked, "Did you study for the test?" Sarah shook her head and said, "Not really... I hope it's easy." When they arrived at school, the teacher greeted them warmly, "Good morning. class!" The students responded, "Good morning, ma'am!"
            """
        ),
    )
}
