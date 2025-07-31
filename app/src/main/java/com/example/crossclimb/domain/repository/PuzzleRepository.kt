package com.example.crossclimb.domain.repository

import com.example.crossclimb.domain.model.Puzzle

interface PuzzleRepository {
    suspend fun getPuzzles(): List<Puzzle>
    suspend fun getPuzzleById(id: String): Puzzle?
}