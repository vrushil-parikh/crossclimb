package com.example.crossclimb.di

import android.content.Context
import com.example.crossclimb.data.local.PuzzleDataSource
import com.example.crossclimb.data.repository.PuzzleRepositoryImpl
import com.example.crossclimb.domain.repository.PuzzleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePuzzleDataSource(context: Context): PuzzleDataSource =
        PuzzleDataSource(context)

    @Provides
    @Singleton
    fun providePuzzleRepository(dataSource: PuzzleDataSource): PuzzleRepository =
        PuzzleRepositoryImpl(dataSource)
}