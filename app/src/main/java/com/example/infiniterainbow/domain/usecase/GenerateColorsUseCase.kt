package com.example.infiniterainbow.domain.usecase

import android.graphics.Color
import kotlin.random.Random

class GenerateColorsUseCase {

    fun execute(
        numOfColors: Int,
        existingColors: Collection<Int> = emptyList()
    ): List<Int> {
        val newColors = mutableSetOf<Int>()
        while (newColors.size < numOfColors) {
            val color = getRandomColor()
            if (color !in existingColors && color !in newColors) {
                newColors.add(color)
            }
        }
        return newColors.toList()
    }

    private fun getRandomColor(): Int {
        return Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }
}
