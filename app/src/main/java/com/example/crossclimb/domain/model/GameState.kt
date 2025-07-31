package com.example.crossclimb.domain.model

// Represents the current state of the game
data class GameState(
    val currentPuzzle: Puzzle?,
    val currentRowIndex: Int = 0,
    val startTime: Long = 0L,
    val elapsedTime: Long = 0L,
    val hintsUsed: Int = 0,
    val rowsRevealed: Int = 0,
    val isGameCompleted: Boolean = false,
    val score: Int = 0
)