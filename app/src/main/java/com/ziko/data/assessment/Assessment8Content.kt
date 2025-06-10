package com.ziko.data.assessment

import com.ziko.ui.model.AssessmentScreenContent

fun getAssessment8Content(): List<AssessmentScreenContent> {
    return listOf(
        AssessmentScreenContent.SpeakAssessment(
            id = 1,
            instructions = "Read the passage and maintain natural rhythm",
            displayText = 
            " The alarm rings.\n\nI stretch and yawn.\n\nThe sun shines bright outside.\n\nI rush downstairs, grab some toast, and sip my tea.\n\nThe city hums with cars and chatter on the street.\n\nA bus rolls past, the driver waves, the doors swing wide.\n\nThe school bell rings, the students run, the lessons start.",
            expectedText = 
            """
            The alarm rings.I stretch and yawn.The sun shines bright outside.I rush downstairs, grab some toast, and sip my tea.The city hums with cars and chatter on the street.A bus rolls past, the driver waves, the doors swing wide.The school bell rings, the students run, the lessons start.
            """,
            audioPath = "lesson/lesson8/photograph.mp3"
        ),

    )
}