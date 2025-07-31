package com.example.crossclimb.data.model

// DTO for saving/loading game state (optional, for persistence)
data class GameStateDto(
    val currentPuzzleId: String?,
    val currentRowIndex: Int = 0,
    val startTime: Long = 0L,
    val elapsedTime: Long = 0L,
    val hintsUsed: Int = 0,
    val rowsRevealed: Int = 0,
    val isGameCompleted: Boolean = false,
    val score: Int = 0
)