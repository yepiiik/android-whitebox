package com.floredo.whitebox.ui.screens.main.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExploreViewModel : BaseViewModel() {
    private val repository = CourseRepository()

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _allCourses = mutableStateOf<List<Course>>(emptyList())
    private val _filteredCourses = mutableStateOf<List<Course>>(emptyList())
    val filteredCourses: State<List<Course>> = _filteredCourses

    val enrolledCourseIds = repository.getAllUserCourses()
        .map { it.map { uc -> uc.courseId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun seedDatabase() {
        viewModelScope.launch {
            val success = repository.seedDatabase()
            if (success) {
                loadCourses()
            }
        }
    }

    init {
        loadCourses()
        refreshUserProgress()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            isLoading.value = true
            val courses = repository.getCourses()
            _allCourses.value = courses
            filterCourses()
            isLoading.value = false
        }
    }

    fun refreshUserProgress() {
        viewModelScope.launch {
            isLoading.value = true
            repository.refreshUserProgress()
            isLoading.value = false
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        filterCourses()
    }

    fun toggleEnrollment(courseId: String) {
        viewModelScope.launch {
            val isEnrolled = enrolledCourseIds.value.contains(courseId)
            if (isEnrolled) {
                repository.unenrollCourse(courseId)
            } else {
                repository.enrollCourse(courseId)
            }
        }
    }

    private fun filterCourses() {
        val query = _searchQuery.value.lowercase()
        val courses = _allCourses.value
        _filteredCourses.value = if (query.isEmpty()) {
            courses
        } else {
            courses.filter { course ->
                course.name.lowercase().contains(query) ||
                        course.description?.lowercase()?.contains(query) == true ||
                        course.category?.name?.lowercase()?.contains(query) == true ||
                        course.level.name.lowercase().contains(query)
            }
        }
    }
}
