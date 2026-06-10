package com.floredo.whitebox.data.models

enum class CourseTypes {
    TECHNICAL,
    HUMANITARIAN,
    SCIENCE
}

enum class CourseLevel {
    EASY,
    MEDIUM,
    HARD
}

data class Course(
    val id: String = "",
    val name: String = "",
    val followers: Int = 0,
    val isFollowed: Boolean = false,
    val modulesCount: Int = 0,
    val level: CourseLevel = CourseLevel.EASY,
    val description: String? = null,
    val category: CourseTypes? = null,
    val thumbnailUrl: String = ""
)
