package com.floredo.whitebox.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String? = null,
    val icon: ImageVector? = null
) {
    // Bottom Nav Screens
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Explore : Screen("explore","Explore", Icons.Default.Search)
    object Profile : Screen("profile", "Profile", Icons.Default.AccountCircle)

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