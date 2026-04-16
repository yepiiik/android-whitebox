package com.floredo.whitebox.data.models

data class Answer(
    val answerText: String,
    val isCorrect: Boolean
)

data class Quiz(
    val id: String,
    val question: String,
    val answers: List<Answer>,
    val moduleId: String
)
