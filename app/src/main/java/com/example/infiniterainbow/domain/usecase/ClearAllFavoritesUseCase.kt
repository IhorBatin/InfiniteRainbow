package com.example.infiniterainbow.domain.usecase

import com.example.infiniterainbow.domain.repository.ColorRepository

class ClearAllFavoritesUseCase(private val repository: ColorRepository) {
    suspend fun execute() {
        repository.clearAllFavorites()
    }
}
