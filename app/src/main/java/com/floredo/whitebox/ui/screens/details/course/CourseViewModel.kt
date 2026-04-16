package com.floredo.whitebox.ui.screens.details.course

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.floredo.whitebox.data.MockData
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.ui.BaseViewModel

class CourseViewModel : BaseViewModel() {
    private val _course = mutableStateOf<Course?>(null)
    val course: State<Course?> = _course

    private val _modules = mutableStateOf<List<Module>>(emptyList())
    val modules: State<List<Module>> = _modules

    fun loadCourse(courseId: String) {
        _course.value = MockData.courses.find { it.id == courseId }
        _modules.value = MockData.modules.filter { it.courseId == courseId }
    }
}