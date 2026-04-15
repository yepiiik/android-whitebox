package com.floredo.whitebox.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String? = null,
    val icon: ImageVector? = null
) {
    // Bottom Nav Screens
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Profile : Screen("profile")

    // Full Screen (No Nav)
    object Module : Screen("module/{moduleId}") {
        fun createRoute(moduleId: String) = "module/$moduleId"
    }
    object Quiz : Screen("quiz/{quizId}") {
        fun createRoute(quizId: String) = "quiz/$quizId"
    }
    object Course : Screen("course/{courseId}") {
        fun createRoute(courseId: String) = "course/$courseId"
    }
}