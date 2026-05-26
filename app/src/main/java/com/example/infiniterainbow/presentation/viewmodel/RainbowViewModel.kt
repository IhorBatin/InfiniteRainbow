package com.example.infiniterainbow.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.infiniterainbow.domain.usecase.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RainbowViewModel(
    private val generateColorsUseCase: GenerateColorsUseCase,
    private val getColorPaletteUseCase: GetColorPaletteUseCase,
    private val getFavoriteColorsUseCase: GetFavoriteColorsUseCase,
    private val toggleFavoriteColorUseCase: ToggleFavoriteColorUseCase,
    private val clearAllFavoritesUseCase: ClearAllFavoritesUseCase
) : ViewModel() {

    // Color List State
    private val initListSize = 75
    private val incrementalAddition = 50
    val colorsList = mutableStateListOf<Int>()

    // Palette State
    var palette by mutableStateOf<GetColorPaletteUseCase.ColorPalette?>(null)
        private set

    // Favorites State
    val favoriteColors: StateFlow<Set<Int>> = getFavoriteColorsUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    init {
        loadInitialColors()
    }

    private fun loadInitialColors() {
        if (colorsList.isEmpty()) {
            colorsList.addAll(generateColorsUseCase.execute(initListSize))
        }
    }

    fun loadMoreColors() {
        colorsList.addAll(generateColorsUseCase.execute(incrementalAddition, colorsList))
    }

    fun initPalette(colorInt: Int) {
        palette = getColorPaletteUseCase.execute(colorInt)
    }

    fun toggleFavorite(color: Int) {
        viewModelScope.launch {
            toggleFavoriteColorUseCase.execute(color)
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            clearAllFavoritesUseCase.execute()
        }
    }

    companion object {
        fun provideFactory(
            generateColorsUseCase: GenerateColorsUseCase,
            getColorPaletteUseCase: GetColorPaletteUseCase,
            getFavoriteColorsUseCase: GetFavoriteColorsUseCase,
            toggleFavoriteColorUseCase: ToggleFavoriteColorUseCase,
            clearAllFavoritesUseCase: ClearAllFavoritesUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RainbowViewModel(
                    generateColorsUseCase,
                    getColorPaletteUseCase,
                    getFavoriteColorsUseCase,
                    toggleFavoriteColorUseCase,
                    clearAllFavoritesUseCase
                ) as T
            }
        }
    }
}
