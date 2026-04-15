package com.floredo.whitebox.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.floredo.whitebox.ui.components.AppBottomBar
import com.floredo.whitebox.ui.navigation.Screen

@Composable
fun MainScreen() {
    // 1. Create the Navigator
    val navController = rememberNavController()

    // 2. Track the current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 3. Define which screens get the Bottom Bar
    val bottomBarScreens = listOf(
        Screen.Home.route,
        Screen.Explore.route,
        Screen.Profile.route
    )
    val showBottomBar = currentRoute in bottomBarScreens

    // 4. Build the Scaffold (The Shell)
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->

        // 5. Build the Navigation Map
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding) // Prevents UI from hiding under the bottom bar
        ) {

            // MAIN SCREENS
            composable(Screen.Home.route) {
                PlaceholderScreen("Home Screen") // Replace with actual HomeScreen later
            }
            composable(Screen.Explore.route) {
                PlaceholderScreen("Explore Screen")
            }
            composable(Screen.Profile.route) {
                PlaceholderScreen("Profile Screen")
            }

            // DETAIL SCREENS (No bottom bar)
            composable(Screen.Module.route) { backStackEntry ->
                val moduleId = backStackEntry.arguments?.getString("moduleId")
                PlaceholderScreen("Module ID: $moduleId")
            }
            composable(Screen.Quiz.route) { backStackEntry ->
                val quizId = backStackEntry.arguments?.getString("quizId")
                PlaceholderScreen("Quiz ID: $quizId")
            }
        }
    }
}

// A temporary helper component so you can test navigation immediately
@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}