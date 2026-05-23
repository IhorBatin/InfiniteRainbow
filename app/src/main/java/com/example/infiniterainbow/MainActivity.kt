package com.example.infiniterainbow

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
import androidx.compose.runtime.*
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
                    colorsList.addAll(generateListOfColors(incrementalAddition))
                }
            }

            LazyColumn(
                state = listState,
                contentPadding = WindowInsets.systemBars.asPaddingValues()
            ) {
                items(colorsList) { colorInt ->
                    ColorCard(colorInt) { onCardClicked(it) }
                }
            }
        }
    }

    private fun onCardClicked(intColor: Int) {
        val hexColor = String.format("#%06X", 0xFFFFFF and intColor)
        val rgbColor = getRgbFromInt(intColor)
        updateToastMsg("RGB: $rgbColor  |  HEX: $hexColor")
    }

    private fun generateListOfColors(numOfColors: Int): MutableList<Int> {
        val colorsList: MutableList<Int> = mutableListOf()
        for (i in 0..numOfColors) colorsList.add(getRandomColor())
        return colorsList
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
}