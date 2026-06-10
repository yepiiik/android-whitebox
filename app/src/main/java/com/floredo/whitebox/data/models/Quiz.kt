package com.floredo.whitebox.data.models

import com.google.firebase.firestore.PropertyName

data class Answer(
    val answerText: String = "",
    @get:PropertyName("isCorrect")
    @set:PropertyName("isCorrect")
    var isCorrect: Boolean = false
)

data class Quiz(
    val id: String = "",
    val question: String = "",
    val answers: List<Answer> = emptyList(),
    val moduleId: String = ""
)
