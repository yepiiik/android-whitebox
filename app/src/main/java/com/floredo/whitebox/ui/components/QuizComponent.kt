package com.floredo.whitebox.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.floredo.whitebox.data.models.Answer
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.ui.theme.WhiteboxTheme

// Custom success color since it's not in standard MaterialTheme colorScheme
val SuccessGreen = Color(0xFF2E7D32)

@Composable
fun QuizComponent(
    quiz: Quiz,
    selectedAnswerIndex: Int?,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = quiz.question,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        quiz.answers.forEachIndexed { index, answer ->
            val isSelected = selectedAnswerIndex == index
            val isRevealed = selectedAnswerIndex != null
            
            val stateColor = when {
                !isRevealed -> if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                answer.isCorrect -> SuccessGreen
                isSelected && !answer.isCorrect -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            }
            
            val backgroundColor = when {
                !isRevealed -> MaterialTheme.colorScheme.surface
                answer.isCorrect -> SuccessGreen.copy(alpha = 0.1f)
                isSelected && !answer.isCorrect -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                else -> MaterialTheme.colorScheme.surface
            }

            Surface(
                onClick = { if (isEnabled) onAnswerSelected(index) },
                shape = RoundedCornerShape(12.dp),
                color = backgroundColor,
                border = BorderStroke(
                    width = if (isSelected || (isRevealed && answer.isCorrect)) 2.dp else 1.dp,
                    color = stateColor
                ),
                enabled = isEnabled && !isRevealed, // Disable interaction after selection
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = null,
                        enabled = isEnabled && !isRevealed,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = if (isRevealed && !answer.isCorrect) MaterialTheme.colorScheme.error else stateColor,
                            unselectedColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    Text(
                        text = answer.answerText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isSelected || (isRevealed && answer.isCorrect)) stateColor else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                    )
                    
                    if (isRevealed && (isSelected || answer.isCorrect)) {
                        Icon(
                            imageVector = if (answer.isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = stateColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Initial State")
@Composable
fun QuizComponentPreview() {
    val quiz = Quiz(
        id = "q1",
        moduleId = "m1",
        question = "What is the capital of France?",
        answers = listOf(
            Answer("Paris", true),
            Answer("London", false),
            Answer("Berlin", false),
            Answer("Madrid", false)
        )
    )
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    
    WhiteboxTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            QuizComponent(
                quiz = quiz,
                selectedAnswerIndex = selectedIndex,
                onAnswerSelected = { selectedIndex = it }
            )
        }
    }
}

@Preview(showBackground = true, name = "Correct Answer Selected")
@Composable
fun QuizComponentCorrectPreview() {
    val quiz = Quiz(
        id = "q1",
        moduleId = "m1",
        question = "What is the capital of France?",
        answers = listOf(
            Answer("Paris", true),
            Answer("London", false),
            Answer("Berlin", false),
            Answer("Madrid", false)
        )
    )
    WhiteboxTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            QuizComponent(
                quiz = quiz,
                selectedAnswerIndex = 0,
                onAnswerSelected = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Incorrect Answer Selected")
@Composable
fun QuizComponentIncorrectPreview() {
    val quiz = Quiz(
        id = "q1",
        moduleId = "m1",
        question = "What is the capital of France?",
        answers = listOf(
            Answer("Paris", true),
            Answer("London", false),
            Answer("Berlin", false),
            Answer("Madrid", false)
        )
    )
    WhiteboxTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            QuizComponent(
                quiz = quiz,
                selectedAnswerIndex = 1,
                onAnswerSelected = {}
            )
        }
    }
}
