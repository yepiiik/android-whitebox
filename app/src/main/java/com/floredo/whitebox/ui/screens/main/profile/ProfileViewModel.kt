package com.floredo.whitebox.ui.screens.main.profile

import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.data.auth.AuthRepository
import com.floredo.whitebox.data.local.entities.ModuleProgress
import com.floredo.whitebox.data.repository.CourseRepository
import com.floredo.whitebox.ui.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class ProfileViewModel : BaseViewModel() {
    private val authRepository = AuthRepository()
    private val courseRepository = CourseRepository()

    val currentUser: StateFlow<FirebaseUser?> = authRepository.currentUser

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()

    private val _activeCoursesCount = MutableStateFlow(0)
    val activeCoursesCount: StateFlow<Int> = _activeCoursesCount.asStateFlow()

    private val _completedModulesCount = MutableStateFlow(0)
    val completedModulesCount: StateFlow<Int> = _completedModulesCount.asStateFlow()

    init {
        viewModelScope.launch {
            courseRepository.getAllUserCourses().collect { enrolled ->
                _activeCoursesCount.value = enrolled.size
            }
        }

        viewModelScope.launch {
            courseRepository.getTotalCompletedModulesCount().collect { count ->
                _completedModulesCount.value = count
            }
        }

        viewModelScope.launch {
            courseRepository.getAllCompletedModules().collect { completedModules ->
                _streak.value = calculateStreak(completedModules)
            }
        }
        
        refreshProgress()
    }

    fun refreshProgress() {
        viewModelScope.launch {
            if (currentUser.value != null) {
                isLoading.value = true
                try {
                    courseRepository.refreshUserProgress()
                } catch (e: Exception) {
                    showError("Refresh failed: ${e.message}")
                } finally {
                    isLoading.value = false
                }
            }
        }
    }

    private fun calculateStreak(completedModules: List<ModuleProgress>): Int {
        if (completedModules.isEmpty()) return 0

        val uniqueDays = completedModules
            .map { 
                val cal = Calendar.getInstance()
                cal.timeInMillis = it.lastUpdated
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis
            }
            .distinct()
            .sortedDescending()

        if (uniqueDays.isEmpty()) return 0

        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val yesterday = today - TimeUnit.DAYS.toMillis(1)

        // If the most recent completion is not today or yesterday, streak is 0
        if (uniqueDays[0] < yesterday) return 0

        var currentStreak = 1
        for (i in 0 until uniqueDays.size - 1) {
            val diff = uniqueDays[i] - uniqueDays[i + 1]
            if (diff == TimeUnit.DAYS.toMillis(1)) {
                currentStreak++
            } else if (diff > TimeUnit.DAYS.toMillis(1)) {
                break
            }
        }

        return currentStreak
    }

    fun signOut() {
        authRepository.signOut()
    }
}
