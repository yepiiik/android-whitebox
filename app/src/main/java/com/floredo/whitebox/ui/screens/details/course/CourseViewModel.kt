package com.floredo.whitebox.ui.screens.details.course

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseViewModel : BaseViewModel() {
    private val repository = CourseRepository()

    private val _course = mutableStateOf<Course?>(null)
    val course: State<Course?> = _course

    private val _modules = mutableStateOf<List<Module>>(emptyList())
    val modules: State<List<Module>> = _modules

    private val _completedModuleIds = MutableStateFlow<Set<String>>(emptySet())
    val completedModuleIds: StateFlow<Set<String>> = _completedModuleIds.asStateFlow()

    fun loadCourse(courseId: String) {
        viewModelScope.launch {
            isLoading.value = true
            repository.refreshUserProgress()
            val allCourses = repository.getCourses()
            _course.value = allCourses.find { it.id == courseId }
            _modules.value = repository.getModules(courseId)
            isLoading.value = false

            repository.getModuleProgressForCourse(courseId).collect { progressList ->
                _completedModuleIds.value = progressList
                    .filter { it.isCompleted }
                    .map { it.moduleId }
                    .toSet()
            }
        }
    }
}
