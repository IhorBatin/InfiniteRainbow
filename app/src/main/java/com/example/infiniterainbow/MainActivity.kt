package com.example.infiniterainbow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.infiniterainbow.components.ColorCard
import com.example.infiniterainbow.ext.isScrolledToTheEnd
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var colorInfoToast: Toast? = null
    private val initListSize = 75
    private val incrementalAddition = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        updateToastMsg(getString(R.string.check_color_value_text))

        setContent {
            val listState = rememberLazyListState()
            val colorsList = remember { mutableStateListOf<Int>()}
            colorsList.addAll(generateListOfColors(initListSize).toList())

            if (listState.isScrolledToTheEnd())
                colorsList.addAll(generateListOfColors(incrementalAddition))

            LazyColumn(state = listState) {
               items(colorsList) {  colorInt ->
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

    private fun generateListOfColors(numOfColors: Int) : MutableList<Int> {
        val colorsList: MutableList<Int> = mutableListOf()
        for (i in 0..numOfColors) colorsList.add(getRandomColor())
        return colorsList
    }

    private fun getRandomColor() : Int {
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