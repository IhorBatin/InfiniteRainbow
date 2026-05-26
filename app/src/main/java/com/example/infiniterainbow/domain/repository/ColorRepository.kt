package com.example.infiniterainbow.domain.repository

import kotlinx.coroutines.flow.Flow

interface ColorRepository {
    fun getFavoriteColors(): Flow<Set<Int>>
    suspend fun toggleFavoriteColor(color: Int)
    suspend fun clearAllFavorites()
}
