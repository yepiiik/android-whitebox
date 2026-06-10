package com.floredo.whitebox.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val repository = CourseRepository()

    private val _enrolledCourses = MutableStateFlow<List<Course>>(emptyList())
    val enrolledCourses: StateFlow<List<Course>> = _enrolledCourses.asStateFlow()

    init {
        refreshProgress()

        viewModelScope.launch {
            // Observe local enrollments
            repository.getAllUserCourses().collect { userCourses ->
                val enrolledIds = userCourses.map { it.courseId }.toSet()
                // Fetch all courses from remote and filter by local enrollments
                val allCourses = repository.getCourses()
                _enrolledCourses.value = allCourses.filter { it.id in enrolledIds }
            }
        }
    }

    fun refreshProgress() {
        viewModelScope.launch {
            isLoading.value = true
            repository.refreshUserProgress()
            isLoading.value = false
        }
    }
}
