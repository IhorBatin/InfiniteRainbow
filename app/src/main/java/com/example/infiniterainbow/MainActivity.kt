package com.example.infiniterainbow

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.infiniterainbow.components.ColorCard
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private var colorInfoToast: Toast? = null
    private val initListSize = 75
    private val incrementalAddition = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        updateToastMsg(getString(R.string.check_color_value_text))

        setContent {
            val listState = rememberLazyListState()
            val colorsList = remember {
                mutableStateListOf<Int>().apply {
                    addAll(generateListOfColors(initListSize))
                }
            }

            // Check if we reached the end of the list and add more colors
            val isAtEnd = remember {
                derivedStateOf {
                    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                    lastVisibleItem != null && lastVisibleItem.index >= colorsList.size - 5
                }
            }

            LaunchedEffect(isAtEnd.value) {
                if (isAtEnd.value) {
                    colorsList.addAll(generateListOfColors(incrementalAddition, colorsList))
                }
            }

            LazyColumn(
                state = listState,
                contentPadding = WindowInsets.systemBars.asPaddingValues()
            ) {
                items(colorsList) { colorInt ->
                    ColorCard(
                        color = colorInt,
                        onCardClick = { onCardClicked(it) },
                        onCopyClick = { onCopyClicked(it) }
                    )
                }
            }
        }
    }

    private fun onCardClicked(intColor: Int) {
        updateToastMsg(formatColorName(intColor))
    }

    private fun onCopyClicked(intColor: Int) {
        val hexColor = formatColorName(intColor)
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(R.string.color), hexColor)
        clipboard.setPrimaryClip(clip)
        updateToastMsg(getString(R.string.copied))
    }

    private fun generateListOfColors(
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

    private fun getRgbFromInt(initColor: Int): String {
        return "(" +
                "${Color.red(initColor)}," +
                "${Color.green(initColor)}," +
                "${Color.blue(initColor)}" +
                ")"
    }

    private fun updateToastMsg(msg: String) {
        if (colorInfoToast != null) colorInfoToast?.cancel()
        colorInfoToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        colorInfoToast?.show()
    }

    private fun formatColorName(colorInt: Int): String {
        val hexColor = String.format("#%06X", 0xFFFFFF and colorInt)
        val rgbColor = getRgbFromInt(colorInt)
        return getString(R.string.full_color_name, rgbColor, hexColor)
    }
}