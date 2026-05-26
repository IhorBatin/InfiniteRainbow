package com.example.infiniterainbow.domain.usecase

import com.example.infiniterainbow.domain.repository.ColorRepository

class ToggleFavoriteColorUseCase(private val repository: ColorRepository) {
    suspend fun execute(color: Int) {
        repository.toggleFavoriteColor(color)
    }
}
