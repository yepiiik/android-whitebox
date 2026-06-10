package com.floredo.whitebox.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.floredo.whitebox.ui.screens.details.course.CourseScreen
import com.floredo.whitebox.ui.screens.details.course.CourseViewModel
import com.floredo.whitebox.ui.screens.details.module.ModuleScreen
import com.floredo.whitebox.ui.screens.details.module.ModuleViewModel
import com.floredo.whitebox.ui.screens.details.quiz.QuizScreen
import com.floredo.whitebox.ui.screens.details.quiz.QuizViewModel
import com.floredo.whitebox.ui.screens.main.home.HomeScreen
import com.floredo.whitebox.ui.screens.main.explore.ExploreScreen
import com.floredo.whitebox.ui.screens.main.profile.ProfileScreen

import com.floredo.whitebox.ui.screens.login.LoginScreen
import com.floredo.whitebox.ui.screens.login.LoginViewModel

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(startDestination = "home", route = "main_route") {
        composable("home") { HomeScreen(navController) }
        composable("explore") { ExploreScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("login") { LoginScreen(navController) }

        composable(
            route = Screen.Course.route,
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            CourseScreen(courseId, navController, viewModel())
        }

        composable(
            route = Screen.Module.route,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: ""
            ModuleScreen(moduleId, navController, viewModel())
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("quizId") { type = NavType.StringType })
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
            QuizScreen(quizId, navController, viewModel())
        }
    }
}