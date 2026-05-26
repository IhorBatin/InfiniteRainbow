package com.example.infiniterainbow.util

import android.graphics.Color
import kotlin.random.Random

fun generateListOfColors(
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

fun getComplementaryColor(colorInt: Int): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(colorInt, hsv)
    hsv[0] = (hsv[0] + 180) % 360
    return Color.HSVToColor(hsv)
}

fun getSimilarColors(colorInt: Int): List<Int> {
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

fun getAnalogousColors(colorInt: Int): List<Int> {
    val hsv = FloatArray(3)
    Color.colorToHSV(colorInt, hsv)

    return listOf(
        Color.HSVToColor(floatArrayOf((hsv[0] + 30) % 360, hsv[1], hsv[2])),
        Color.HSVToColor(floatArrayOf((hsv[0] - 30 + 360) % 360, hsv[1], hsv[2]))
    )
}

private fun getRandomColor(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}
