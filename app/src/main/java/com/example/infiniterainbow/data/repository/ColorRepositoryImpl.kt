package com.example.infiniterainbow.data.repository

import com.example.infiniterainbow.data.local.FavoritesDataSource
import com.example.infiniterainbow.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class ColorRepositoryImpl(private val favoritesDataSource: FavoritesDataSource) : ColorRepository {
    override fun getFavoriteColors(): Flow<Set<Int>> = favoritesDataSource.getFavoriteColors()
    override suspend fun toggleFavoriteColor(color: Int) = favoritesDataSource.toggleFavoriteColor(color)
    override suspend fun clearAllFavorites() = favoritesDataSource.clearAllFavorites()
}
