package com.example.crossclimb.data.model

// DTO for a puzzle set loaded from JSON

data class PuzzleDto(
    val id: String,
    val title: String,
    val theme: String,
    val rows: List<RowDto>
)