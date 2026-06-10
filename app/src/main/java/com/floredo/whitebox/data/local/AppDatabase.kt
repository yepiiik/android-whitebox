package com.floredo.whitebox.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.floredo.whitebox.data.local.dao.CourseDao
import com.floredo.whitebox.data.local.entities.ModuleProgress
import com.floredo.whitebox.data.local.entities.UserCourse

@Database(entities = [UserCourse::class, ModuleProgress::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
