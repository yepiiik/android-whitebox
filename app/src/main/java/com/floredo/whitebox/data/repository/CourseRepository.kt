package com.floredo.whitebox.data.repository

import com.floredo.whitebox.WhiteboxApp
import com.floredo.whitebox.data.auth.AuthRepository
import com.floredo.whitebox.data.local.entities.ModuleProgress
import com.floredo.whitebox.data.local.entities.UserCourse
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.data.remote.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class CourseRepository {
    private val courseDao = WhiteboxApp.instance.database.courseDao()
    private val remoteDataSource = FirestoreRepository()
    private val authRepository = AuthRepository()

    suspend fun getCourses(): List<Course> = remoteDataSource.getCourses()

    suspend fun seedDatabase(): Boolean = remoteDataSource.seedDatabase()

    suspend fun getModules(courseId: String): List<Module> = remoteDataSource.getModules(courseId)

    suspend fun getModuleById(moduleId: String): Module? = remoteDataSource.getModuleById(moduleId)

    suspend fun getQuiz(moduleId: String): Quiz? = remoteDataSource.getQuiz(moduleId)

    suspend fun getQuizById(quizId: String): Quiz? = remoteDataSource.getQuizById(quizId)

    fun getAllUserCourses(): Flow<List<UserCourse>> = courseDao.getAllUserCourses()

    suspend fun enrollCourse(courseId: String) {
        val userCourse = UserCourse(courseId)
        courseDao.enrollCourse(userCourse)
        
        authRepository.currentUser.value?.uid?.let { userId ->
            remoteDataSource.saveUserCourse(userId, userCourse)
        }
    }

    suspend fun unenrollCourse(courseId: String) {
        courseDao.unenrollCourse(courseId)
        
        authRepository.currentUser.value?.uid?.let { userId ->
            remoteDataSource.deleteUserCourse(userId, courseId)
        }
    }

    fun getModuleProgressForCourse(courseId: String): Flow<List<ModuleProgress>> =
        courseDao.getModuleProgressForCourse(courseId)

    suspend fun updateModuleProgress(courseId: String, moduleId: String, isCompleted: Boolean) {
        val progress = ModuleProgress(
            courseId = courseId,
            moduleId = moduleId,
            isCompleted = isCompleted
        )
        courseDao.updateModuleProgress(progress)
        
        authRepository.currentUser.value?.uid?.let { userId ->
            remoteDataSource.saveModuleProgress(userId, progress)
        }
    }

    fun getTotalCompletedModulesCount(): Flow<Int> = courseDao.getTotalCompletedModulesCount()

    fun getAllCompletedModules(): Flow<List<ModuleProgress>> = courseDao.getAllCompletedModules()

    suspend fun refreshUserProgress() {
        authRepository.currentUser.value?.uid?.let { userId ->
            try {
                val remoteCourses = remoteDataSource.getUserCourses(userId)
                val remoteProgress = remoteDataSource.getUserProgress(userId)

                // Perform updates in Room. 
                // To handle deletions from other devices, we sync the state.
                
                // 1. Sync Enrolled Courses
                courseDao.deleteAllUserCourses()
                remoteCourses.forEach { courseDao.enrollCourse(it) }

                // 2. Sync Module Progress
                courseDao.deleteAllModuleProgress()
                remoteProgress.forEach { courseDao.updateModuleProgress(it) }
            } catch (e: Exception) {
                // Handle or log error - if offline, we keep what we have in Room
            }
        }
    }
}
