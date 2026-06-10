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
import com.floredo.whitebox.ui.components.ModuleDetailSkeleton
import com.floredo.whitebox.ui.components.QuizComponent
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    moduleId: String,
    navController: NavController,
    viewModel: ModuleViewModel
) {
    val module by viewModel.module
    val quiz by viewModel.quiz
    val isCompleted by viewModel.isCompleted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var quizResult by remember(moduleId) { mutableStateOf<String?>(null) }
    var hasPassedQuiz by remember(moduleId) { mutableStateOf(false) }
    var selectedAnswerIndex by remember(moduleId) { mutableStateOf<Int?>(null) }

    LaunchedEffect(moduleId) {
        viewModel.loadModule(moduleId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(module?.name ?: if (isLoading) "" else "Module") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { paddingValues ->
        if (isLoading && module == null) {
            Box(modifier = Modifier.padding(paddingValues)) {
                ModuleDetailSkeleton()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Module Content
                module?.content?.let { content ->
                    MarkdownText(
                        markdown = content,
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
                        selectedAnswerIndex = selectedAnswerIndex,
                        onAnswerSelected = { index ->
                            selectedAnswerIndex = index
                            val isCorrect = quiz!!.answers[index].isCorrect
                            quizResult = if (isCorrect) "Correct! 🎉" else "Try again! ❌"
                            hasPassedQuiz = isCorrect
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
                    onClick = {
                        viewModel.completeModule()
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isCompleted && (quiz == null || hasPassedQuiz)
                ) {
                    Text(
                        text = when {
                            isCompleted -> "Module Completed"
                            quiz != null && !hasPassedQuiz -> "Pass Quiz to Complete"
                            else -> "Complete Module"
                        }
                    )
                }
            }
        }
    }
}
