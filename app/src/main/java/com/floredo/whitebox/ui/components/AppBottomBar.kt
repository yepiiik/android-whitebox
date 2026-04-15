package com.floredo.whitebox.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.floredo.whitebox.ui.navigation.Screen

@Composable
fun AppBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.Home,
        Screen.Explore,
        Screen.Profile
    )

    // Here there was critical issue that has been solved by adding "buildFeatures {compose = true}" to build.gradle.kts
    NavigationBar(
        content = {
            items.forEach { screen ->
                this.NavigationBarItem(
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { screen.icon?.let { Icon(imageVector = it, contentDescription = null) } },
                    label = { screen.label?.let { Text(text = it) } }
                )
            }
        }
    )
}