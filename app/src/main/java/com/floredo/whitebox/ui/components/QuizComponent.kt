package com.floredo.whitebox.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.floredo.whitebox.data.models.Quiz

@Composable
fun QuizComponent(
    quiz: Quiz,
    onAnswerSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = quiz.question,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(Modifier.selectableGroup()) {
            quiz.answers.forEachIndexed { index, answer ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (selectedAnswer == index),
                            onClick = { 
                                selectedAnswer = index
                                onAnswerSelected(answer.isCorrect)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedAnswer == index),
                        onClick = null // null recommended for accessibility with selectable modifier
                    )
                    Text(
                        text = answer.answerText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}
