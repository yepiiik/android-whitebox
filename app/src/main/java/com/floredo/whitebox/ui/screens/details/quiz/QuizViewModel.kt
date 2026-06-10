package com.floredo.whitebox.ui.screens.details.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import kotlinx.coroutines.launch

class QuizViewModel : BaseViewModel() {
    private val repository = CourseRepository()
    private val _quiz = mutableStateOf<Quiz?>(null)
    val quiz: State<Quiz?> = _quiz

    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            _quiz.value = repository.getQuizById(quizId)
        }
    }
}