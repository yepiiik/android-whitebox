package com.floredo.whitebox.data.local.entities

import androidx.room.Entity

@Entity(tableName = "module_progress", primaryKeys = ["courseId", "moduleId"])
data class ModuleProgress(
    val courseId: String = "",
    val moduleId: String = "",
    val isCompleted: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)
