package com.example.crossclimb.domain.model

// Represents a single row in a puzzle
data class Row(
    val id: Int,
    val clue: String,
    val answer: String,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val revealedLetters: Int = 0,
    val userInput: String = ""
)