package com.example.crossclimb.domain.usecase

import com.example.crossclimb.domain.model.Puzzle
import com.example.crossclimb.domain.repository.PuzzleRepository

class LoadPuzzleUseCase(private val repository: PuzzleRepository) {
    suspend operator fun invoke(): List<Puzzle> = repository.getPuzzles()
}