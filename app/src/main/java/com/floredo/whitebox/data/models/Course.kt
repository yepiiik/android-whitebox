package com.floredo.whitebox.data.models

enum class CourseTypes {
    TECHNICAL,
    HUMANITARIAN
}

enum class CourseLevel {
    EASE,
    MEDIUM,
    HARD
}

data class Course(
    val name: String,
    val description: String,
    val category: CourseTypes,
    val followers: Int,
    val isFollowed: Boolean,
    val modulesCount: Int,
    val level: CourseLevel
)
