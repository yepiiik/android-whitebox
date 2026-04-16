package com.floredo.whitebox.ui.screens.details.quiz

import androidx.compose.foundation.layout.*
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
fun QuizScreen(
    quizId: String,
    navController: NavController,
    viewModel: QuizViewModel
) {
    val quiz by viewModel.quiz
    var quizResult by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            quiz?.let {
                QuizComponent(
                    quiz = it,
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                enabled = quizResult?.contains("Correct") == true
            ) {
                Text("Finish Quiz")
            }
        }
    }
}