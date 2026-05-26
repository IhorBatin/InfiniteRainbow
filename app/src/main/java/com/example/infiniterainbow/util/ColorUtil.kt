package com.example.infiniterainbow.util

import android.graphics.Color

fun getRgbString(colorInt: Int): String {
    return "(" +
            "${Color.red(colorInt)}," +
            "${Color.green(colorInt)}," +
            "${Color.blue(colorInt)}" +
            ")"
}

fun getHexString(colorInt: Int): String {
    return String.format("#%06X", 0xFFFFFF and colorInt)
}
