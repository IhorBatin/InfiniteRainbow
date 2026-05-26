package com.example.infiniterainbow.domain.usecase

import com.example.infiniterainbow.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteColorsUseCase(private val repository: ColorRepository) {
    fun execute(): Flow<Set<Int>> {
        return repository.getFavoriteColors()
    }
}
