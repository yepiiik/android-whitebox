package com.floredo.whitebox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.floredo.whitebox.data.local.entities.ModuleProgress
import com.floredo.whitebox.data.local.entities.UserCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM user_courses")
    fun getAllUserCourses(): Flow<List<UserCourse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enrollCourse(userCourse: UserCourse)

    @Query("DELETE FROM user_courses WHERE courseId = :courseId")
    suspend fun unenrollCourse(courseId: String)

    @Query("SELECT * FROM module_progress WHERE courseId = :courseId")
    fun getModuleProgressForCourse(courseId: String): Flow<List<ModuleProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateModuleProgress(progress: ModuleProgress)

    @Query("SELECT COUNT(*) FROM module_progress WHERE isCompleted = 1")
    fun getTotalCompletedModulesCount(): Flow<Int>

    @Query("SELECT * FROM module_progress WHERE isCompleted = 1 ORDER BY lastUpdated DESC")
    fun getAllCompletedModules(): Flow<List<ModuleProgress>>

    @Query("DELETE FROM user_courses")
    suspend fun deleteAllUserCourses()

    @Query("DELETE FROM module_progress")
    suspend fun deleteAllModuleProgress()
}
