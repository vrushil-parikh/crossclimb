package com.example.crossclimb.domain.usecase

class CalculateScoreUseCase {
    operator fun invoke(baseScore: Int, hintsUsed: Int, rowsRevealed: Int, elapsedSeconds: Int): Int {
        val score = baseScore - (hintsUsed * 50) - (rowsRevealed * 100) - (elapsedSeconds * 2)
        return score.coerceAtLeast(0)
    }
}