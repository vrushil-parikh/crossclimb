package com.example.crossclimb.presentation.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crossclimb.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel()) {
    val state = viewModel.gameState.collectAsState().value
    val puzzle = state.currentPuzzle

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = puzzle?.title ?: "Loading...", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        puzzle?.rows?.forEachIndexed { index, row ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Clue: ${row.clue}")
                    TextField(
                        value = row.userInput,
                        onValueChange = { viewModel.submitAnswer(index, it) },
                        enabled = row.isUnlocked && !row.isCompleted,
                        label = { Text("Your Answer") }
                    )
                    if (row.isCompleted) {
                        Text(text = "Correct!", color = MaterialTheme.colors.primary)
                    }
                    if (row.isUnlocked && !row.isCompleted) {
                        Button(onClick = { viewModel.useHint(index) }) {
                            Text("Hint")
                        }
                        if (row.revealedLetters > 0) {
                            Text(text = "Hint: ${row.answer.take(row.revealedLetters)}")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.calculateScore() }) {
            Text("Finish & Calculate Score")
        }
        Text(text = "Score: ${state.score}")
    }
}