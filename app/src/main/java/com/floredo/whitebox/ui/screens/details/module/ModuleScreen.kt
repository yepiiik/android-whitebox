package com.floredo.whitebox.ui.screens.details.module

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floredo.whitebox.ui.components.QuizComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    moduleId: String,
    navController: NavController,
    viewModel: ModuleViewModel
) {
    val module by viewModel.module
    val quiz by viewModel.quiz
    var quizResult by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(moduleId) {
        viewModel.loadModule(moduleId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(module?.name ?: "Module") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Module Content
            module?.content?.forEach { contentItem ->
                Text(
                    text = contentItem.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (quiz != null) {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Quick Quiz",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                QuizComponent(
                    quiz = quiz!!,
                    onAnswerSelected = { isCorrect ->
                        quizResult = if (isCorrect) "Correct! 🎉" else "Try again! ❌"
                    }
                )

                quizResult?.let { result ->
                    Text(
                        text = result,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (result.contains("Correct"))
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Complete Module")
            }
        }
    }
}