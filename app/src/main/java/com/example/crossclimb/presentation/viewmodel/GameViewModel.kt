package com.example.crossclimb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossclimb.domain.model.GameState
import com.example.crossclimb.domain.model.Puzzle
import com.example.crossclimb.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val loadPuzzleUseCase: LoadPuzzleUseCase,
    private val validateAnswerUseCase: ValidateAnswerUseCase,
    private val getHintUseCase: GetHintUseCase,
    private val calculateScoreUseCase: CalculateScoreUseCase
) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState(currentPuzzle = null))
    val gameState: StateFlow<GameState> = _gameState

    fun loadPuzzles() {
        viewModelScope.launch {
            val puzzles = loadPuzzleUseCase()
            if (puzzles.isNotEmpty()) {
                _gameState.value = _gameState.value.copy(currentPuzzle = puzzles[0])
            }
        }
    }

    fun submitAnswer(rowIndex: Int, answer: String) {
        val puzzle = _gameState.value.currentPuzzle ?: return
        val row = puzzle.rows[rowIndex]
        val isCorrect = validateAnswerUseCase(answer, row.answer)
        if (isCorrect) {
            val updatedRows = puzzle.rows.mapIndexed { idx, r ->
                if (idx == rowIndex) r.copy(isCompleted = true, userInput = answer)
                else if (idx == rowIndex + 1) r.copy(isUnlocked = true)
                else r
            }
            _gameState.value = _gameState.value.copy(
                currentPuzzle = puzzle.copy(rows = updatedRows),
                currentRowIndex = rowIndex + 1
            )
        } else {
            val updatedRows = puzzle.rows.mapIndexed { idx, r ->
                if (idx == rowIndex) r.copy(userInput = answer) else r
            }
            _gameState.value = _gameState.value.copy(
                currentPuzzle = puzzle.copy(rows = updatedRows)
            )
        }
    }

    fun useHint(rowIndex: Int) {
        val puzzle = _gameState.value.currentPuzzle ?: return
        val row = puzzle.rows[rowIndex]
        val nextHint = getHintUseCase(row.answer, row.revealedLetters)
        val updatedRows = puzzle.rows.mapIndexed { idx, r ->
            if (idx == rowIndex) r.copy(revealedLetters = nextHint.length) else r
        }
        _gameState.value = _gameState.value.copy(
            currentPuzzle = puzzle.copy(rows = updatedRows),
            hintsUsed = _gameState.value.hintsUsed + 1
        )
    }

    fun calculateScore() {
        val state = _gameState.value
        val puzzle = state.currentPuzzle ?: return
        val elapsedSeconds = (state.elapsedTime / 1000).toInt()
        val score = calculateScoreUseCase(
            baseScore = 1000,
            hintsUsed = state.hintsUsed,
            rowsRevealed = state.rowsRevealed,
            elapsedSeconds = elapsedSeconds
        )
        _gameState.value = state.copy(score = score)
    }
}