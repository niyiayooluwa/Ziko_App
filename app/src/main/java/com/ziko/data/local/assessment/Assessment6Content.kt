package com.ziko.data.local.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment6Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Speak this sentence",
            displayText = "What time is it?",
            expectedText = "What time is it",
            audioPath = "lesson/lesson6/what_time_is_it.mp3"
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 2,
            instructions = "Speak this sentence",
            displayText = "She loves reading books",
            expectedText = "She loves reading books",
            audioPath = "lesson/lesson6/she_loves_reading_books.mp3"
        ),

        AssessmentScreenContent.McqAssessment(
            id = 3,
            instructions = "Identify the intonation pattern in this sentence",
            question = "What time is it?",
            options = listOf("Rising", "Falling", "Mixed"),
            correctAnswer = "Rising",
        ),

        AssessmentScreenContent.McqAssessment(
            id = 4,
            instructions = "Identify the intonation pattern in this sentence",
            question = "She loves reading books",
            options = listOf("Rising", "Falling", "Mixed"),
            correctAnswer = "Falling",
        ),

        AssessmentScreenContent.SpeakAssessment(
            id = 5,
            instructions = "Read the passage with correct intonation",
            displayText = 
            """It was a bright morning, and Sarah was getting ready for school. She asked her mother, "Where is my backpack?" Her mother replied, "It's on the table." Sarah picked up her backpack and said, "Oh no! I forgot my homework!" Her brother laughed and asked, "Again?" She sighed and said, "Yes, I left it in my room." She ran upstairs, grabbed her homework, and hurried out the door. On the way to school, her friend Emma asked, "Did you study for the test?" Sarah shook her head and said, "Not really... I hope it's easy." When they arrived at school, the teacher greeted them warmly, "Good morning. class!" The students responded, "Good morning, ma'am!"
            """,
            expectedText = 
            """It was a bright morning, and Sarah was getting ready for school. She asked her mother, "Where is my backpack?" Her mother replied, "It's on the table." Sarah picked up her backpack and said, "Oh no! I forgot my homework!" Her brother laughed and asked, "Again?" She sighed and said, "Yes, I left it in my room." She ran upstairs, grabbed her homework, and hurried out the door. On the way to school, her friend Emma asked, "Did you study for the test?" Sarah shook her head and said, "Not really... I hope it's easy." When they arrived at school, the teacher greeted them warmly, "Good morning. class!" The students responded, "Good morning, ma'am!"
            """,
            audioPath = "lesson/lesson6/it_was_a_bright_morning.mp3"
        )
    )
}