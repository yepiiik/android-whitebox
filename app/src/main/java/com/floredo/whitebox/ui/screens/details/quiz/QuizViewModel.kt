package com.floredo.whitebox.ui.screens.details.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.floredo.whitebox.data.MockData
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.ui.BaseViewModel

class QuizViewModel : BaseViewModel() {
    private val _quiz = mutableStateOf<Quiz?>(null)
    val quiz: State<Quiz?> = _quiz

    fun loadQuiz(quizId: String) {
        _quiz.value = MockData.quizzes.find { it.id == quizId }
    }
}