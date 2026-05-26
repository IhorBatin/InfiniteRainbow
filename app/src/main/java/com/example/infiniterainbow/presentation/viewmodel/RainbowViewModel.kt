package com.example.infiniterainbow.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.infiniterainbow.domain.usecase.GenerateColorsUseCase
import com.example.infiniterainbow.domain.usecase.GetColorPaletteUseCase

class RainbowViewModel(
    private val generateColorsUseCase: GenerateColorsUseCase = GenerateColorsUseCase(),
    private val getColorPaletteUseCase: GetColorPaletteUseCase = GetColorPaletteUseCase()
) : ViewModel() {

    // Color List State
    private val initListSize = 75
    private val incrementalAddition = 50
    val colorsList = mutableStateListOf<Int>()

    // Palette State
    var palette by mutableStateOf<GetColorPaletteUseCase.ColorPalette?>(null)
        private set

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
}
