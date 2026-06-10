package com.floredo.whitebox.ui.screens.details.module

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.local.entities.ModuleProgress
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModuleViewModel : BaseViewModel() {
    private val repository = CourseRepository()

    private val _module = mutableStateOf<Module?>(null)
    val module: State<Module?> = _module

    private val _quiz = mutableStateOf<Quiz?>(null)
    val quiz: State<Quiz?> = _quiz

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted.asStateFlow()

    fun loadModule(moduleId: String) {
        viewModelScope.launch {
            isLoading.value = true
            repository.refreshUserProgress()
            val mod = repository.getModuleById(moduleId)
            _module.value = mod
            _quiz.value = repository.getQuiz(moduleId)
            isLoading.value = false

            mod?.let { m ->
                repository.getModuleProgressForCourse(m.courseId).collect { progressList ->
                    _isCompleted.value = progressList.any { it.moduleId == moduleId && it.isCompleted }
                }
            }
        }
    }

    fun completeModule() {
        val mod = _module.value ?: return
        viewModelScope.launch {
            repository.updateModuleProgress(mod.courseId, mod.id, true)
        }
    }
}
