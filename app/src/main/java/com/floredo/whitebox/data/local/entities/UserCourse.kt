package com.floredo.whitebox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_courses")
data class UserCourse(
    @PrimaryKey val courseId: String = "",
    val enrollmentDate: Long = System.currentTimeMillis()
)
