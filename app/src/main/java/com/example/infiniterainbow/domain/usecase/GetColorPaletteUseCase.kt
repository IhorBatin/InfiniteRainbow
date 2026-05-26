package com.example.infiniterainbow.domain.usecase

import android.graphics.Color

class GetColorPaletteUseCase {
    
    data class ColorPalette(
        val original: Int,
        val complementary: Int,
        val similar: List<Int>,
        val analogous: List<Int>
    )

    fun execute(colorInt: Int): ColorPalette {
        return ColorPalette(
            original = colorInt,
            complementary = getComplementaryColor(colorInt),
            similar = getSimilarColors(colorInt),
            analogous = getAnalogousColors(colorInt)
        )
    }

    private fun getComplementaryColor(colorInt: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(colorInt, hsv)
        hsv[0] = (hsv[0] + 180) % 360
        return Color.HSVToColor(hsv)
    }

    private fun getSimilarColors(colorInt: Int): List<Int> {
        val hsv = FloatArray(3)
        Color.colorToHSV(colorInt, hsv)

        return listOf(
            // Slightly lighter & less saturated
            Color.HSVToColor(floatArrayOf(hsv[0], (hsv[1] * 0.8f).coerceIn(0f, 1f), (hsv[2] * 1.2f).coerceIn(0f, 1f))),
            // Slightly darker & more saturated
            Color.HSVToColor(floatArrayOf(hsv[0], (hsv[1] * 1.2f).coerceIn(0f, 1f), (hsv[2] * 0.8f).coerceIn(0f, 1f))),
            // More saturated
            Color.HSVToColor(floatArrayOf(hsv[0], (hsv[1] * 1.4f).coerceIn(0f, 1f), hsv[2])),
            // Less saturated
            Color.HSVToColor(floatArrayOf(hsv[0], (hsv[1] * 0.6f).coerceIn(0f, 1f), hsv[2])),
            // Pure Tint (Highlight)
            Color.HSVToColor(floatArrayOf(hsv[0], hsv[1], 0.95f)),
            // Pure Shade (Shadow)
            Color.HSVToColor(floatArrayOf(hsv[0], hsv[1], 0.30f))
        )
    }

    private fun getAnalogousColors(colorInt: Int): List<Int> {
        val hsv = FloatArray(3)
        Color.colorToHSV(colorInt, hsv)

        return listOf(
            Color.HSVToColor(floatArrayOf((hsv[0] + 30) % 360, hsv[1], hsv[2])),
            Color.HSVToColor(floatArrayOf((hsv[0] - 30 + 360) % 360, hsv[1], hsv[2]))
        )
    }
}
