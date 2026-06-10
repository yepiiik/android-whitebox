package com.floredo.whitebox.ui.screens.details.course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import com.floredo.whitebox.data.MockData
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.ui.components.CourseDetailSkeleton
import com.floredo.whitebox.ui.theme.WhiteboxTheme
import com.floredo.whitebox.ui.components.ModuleListItem
import com.floredo.whitebox.ui.navigation.Screen
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun CourseScreen(
    courseId: String,
    navController: NavController,
    viewModel: CourseViewModel
) {
    val course by viewModel.course
    val modules by viewModel.modules
    val completedModuleIds by viewModel.completedModuleIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(courseId) {
        viewModel.loadCourse(courseId)
    }

    CourseScreenContent(
        course = course,
        modules = modules,
        isLoading = isLoading,
        completedModuleIds = completedModuleIds,
        onBackClick = { navController.popBackStack() },
        onModuleClick = { module ->
            navController.navigate(Screen.Module.createRoute(module.id)) {
                launchSingleTop = true
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseScreenContent(
    course: Course?,
    modules: List<Module>,
    isLoading: Boolean,
    completedModuleIds: Set<String>,
    onBackClick: () -> Unit,
    onModuleClick: (Module) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course?.name ?: if (isLoading) "" else "Course Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        if (isLoading && course == null) {
            Box(modifier = Modifier.padding(paddingValues)) {
                CourseDetailSkeleton()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    course?.description?.let {
                        MarkdownText(
                            markdown = it,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Text(
                        text = "Modules",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(modules) { module ->
                    ModuleListItem(
                        module = module,
                        onModuleClick = { onModuleClick(module) },
                        isCompleted = module.id in completedModuleIds
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CourseScreenPreview() {
    WhiteboxTheme {
        CourseScreenContent(
            course = MockData.courses[0],
            modules = MockData.modules.filter { it.courseId == MockData.courses[0].id },
            isLoading = false,
            completedModuleIds = emptySet(),
            onBackClick = {},
            onModuleClick = {}
        )
    }
}
