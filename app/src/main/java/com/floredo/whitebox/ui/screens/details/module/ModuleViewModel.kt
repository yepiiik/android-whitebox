package com.floredo.whitebox.ui.screens.details.module

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.floredo.whitebox.data.MockData
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.ui.BaseViewModel

class ModuleViewModel : BaseViewModel() {
    private val _module = mutableStateOf<Module?>(null)
    val module: State<Module?> = _module

    private val _quiz = mutableStateOf<Quiz?>(null)
    val quiz: State<Quiz?> = _quiz

    fun loadModule(moduleId: String) {
        _module.value = MockData.modules.find { it.id == moduleId }
        _quiz.value = MockData.quizzes.find { it.moduleId == moduleId }
    }
}