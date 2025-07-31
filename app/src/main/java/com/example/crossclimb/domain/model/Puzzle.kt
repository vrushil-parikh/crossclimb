package com.example.crossclimb.domain.model

// Represents a puzzle with 6 rows
data class Puzzle(
    val id: String,
    val title: String,
    val theme: String,
    val rows: List<Row>
)