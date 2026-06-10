package com.floredo.whitebox.ui.screens.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.floredo.whitebox.data.MockData
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.ui.components.CourseCard
import com.floredo.whitebox.ui.components.CourseCardSkeleton
import com.floredo.whitebox.ui.theme.WhiteboxTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.floredo.whitebox.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val courses by viewModel.enrolledCourses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshProgress()
    }
    
    HomeScreenContent(
        courses = courses,
        isLoading = isLoading,
        onCourseClick = { course ->
            navController.navigate(Screen.Course.createRoute(course.id)) {
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    courses: List<Course>,
    isLoading: Boolean,
    onCourseClick: (Course) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Active Courses",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (isLoading && courses.isEmpty()) {
                items(5) {
                    CourseCardSkeleton()
                }
            } else {
                items(courses) { course ->
                    CourseCard(
                        course = course,
                        onClick = { onCourseClick(course) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WhiteboxTheme {
        HomeScreenContent(
            courses = MockData.courses,
            isLoading = false,
            onCourseClick = {}
        )
    }
}
