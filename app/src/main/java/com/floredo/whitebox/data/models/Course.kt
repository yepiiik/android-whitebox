package com.floredo.whitebox.data.models

import androidx.compose.ui.text.LinkAnnotation

enum class CourseTypes {
    TECHNICAL,
    HUMANITARIAN
}

enum class CourseLevel {
    EASY,
    MEDIUM,
    HARD
}

data class Course(
    val id: String,
    val name: String,
    val followers: Int,
    val isFollowed: Boolean,
    val modulesCount: Int,
    val level: CourseLevel,
    val description: String?,
    val category: CourseTypes?,
    val thumbnailUrl: LinkAnnotation.Url
)
