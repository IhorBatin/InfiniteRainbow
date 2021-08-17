package com.example.infiniterainbow

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infiniterainbow.adapter.ColorsListAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var colorInfoToast: Toast? = null
    private val initListSize = 75
    private val incrementalAddition = 50
    private lateinit var rvColorsList: RecyclerView
    private lateinit var rvAdapter: ColorsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        setupRecyclerView()
        setupOnScrollListener()
        setupOnClickListeners()
        showGreetingToast()
    }

    private fun setupOnClickListeners() {
        rvAdapter.onCardClicked = { card ->
            val intColor = card.cardBackgroundColor.defaultColor
            val hexColor = String.format("#%06X", 0xFFFFFF and intColor)
            val rgbColor = getRgbFromInt(intColor)
            updateToastMsg("RGB: $rgbColor  |  HEX: $hexColor")
        }
    }

    private fun setupOnScrollListener() {
        rvColorsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvColorsList.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Reached end of the list, adding more colors :)
                    rvAdapter.addMoreItems(generateListOfColors(incrementalAddition))
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rvAdapter = ColorsListAdapter(generateListOfColors(initListSize))
        rvColorsList = findViewById<RecyclerView>(R.id.rvColorsList).apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
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

    private fun showGreetingToast() {
        val toast = Toast.makeText(this, getString(R.string.check_color_value_text), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()
    }

    @SuppressLint("ShowToast")
    private fun updateToastMsg(msg: String) {
        if (colorInfoToast != null) colorInfoToast?.cancel()
        colorInfoToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        colorInfoToast?.show()
    }
}