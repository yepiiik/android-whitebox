package com.floredo.whitebox.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.floredo.whitebox.ui.screens.main.home.HomeScreen
import com.floredo.whitebox.ui.screens.main.explore.ExploreScreen
import com.floredo.whitebox.ui.screens.main.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(startDestination = "home", route = "main_route") {
        composable("home") { HomeScreen(navController) }
        composable("courses") { ExploreScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}