package com.floredo.whitebox.ui.screens.main.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.floredo.whitebox.ui.components.CourseCard
import com.floredo.whitebox.ui.components.CourseCardSkeleton
import com.floredo.whitebox.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = viewModel()
) {
    val searchQuery by viewModel.searchQuery
    val filteredCourses by viewModel.filteredCourses
    val enrolledCourseIds by viewModel.enrolledCourseIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshUserProgress()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore") },
                actions = {
                    // Temporary Debug Button to Seed Firestore
                    IconButton(onClick = { viewModel.seedDatabase() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Seed DB")
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search courses...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            // Course List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (isLoading && filteredCourses.isEmpty()) {
                    items(5) {
                        CourseCardSkeleton()
                    }
                } else {
                    items(filteredCourses) { course ->
                        CourseCard(
                            course = course,
                            onClick = {
                                navController.navigate(Screen.Course.createRoute(course.id)) {
                                    launchSingleTop = true
                                }
                            },
                            isEnrolled = enrolledCourseIds.contains(course.id),
                            onEnrollToggle = { viewModel.toggleEnrollment(course.id) }
                        )
                    }
                }
            }
        }
    }
}
