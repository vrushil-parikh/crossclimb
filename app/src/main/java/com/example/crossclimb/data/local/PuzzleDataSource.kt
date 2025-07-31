package com.example.crossclimb.data.local

import android.content.Context
import com.example.crossclimb.data.model.PuzzleDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PuzzleDataSource(private val context: Context) {
    suspend fun loadPuzzles(): List<PuzzleDto> {
        val jsonString = context.assets.open("puzzle_sets.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<PuzzleSetWrapper>() {}.type
        val wrapper: PuzzleSetWrapper = Gson().fromJson(jsonString, type)
        return wrapper.puzzleSets
    }

    private data class PuzzleSetWrapper(
        val puzzleSets: List<PuzzleDto>
    )
}