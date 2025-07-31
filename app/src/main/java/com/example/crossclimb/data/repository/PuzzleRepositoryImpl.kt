package com.example.crossclimb.data.repository

import com.example.crossclimb.data.local.PuzzleDataSource
import com.example.crossclimb.data.model.PuzzleDto
import com.example.crossclimb.domain.model.Puzzle
import com.example.crossclimb.domain.model.Row
import com.example.crossclimb.domain.repository.PuzzleRepository

class PuzzleRepositoryImpl(private val dataSource: PuzzleDataSource) : PuzzleRepository {
    override suspend fun getPuzzles(): List<Puzzle> {
        return dataSource.loadPuzzles().map { it.toDomain() }
    }

    override suspend fun getPuzzleById(id: String): Puzzle? {
        return dataSource.loadPuzzles().find { it.id == id }?.toDomain()
    }
}

// Mapping extension function
private fun PuzzleDto.toDomain(): Puzzle = Puzzle(
    id = id,
    title = title,
    theme = theme,
    rows = rows.map { it.toDomain() }
)

private fun com.example.crossclimb.data.model.RowDto.toDomain(): Row = Row(
    id = id,
    clue = clue,
    answer = answer
)