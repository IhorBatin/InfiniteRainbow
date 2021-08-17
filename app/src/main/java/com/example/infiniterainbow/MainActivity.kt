package com.example.infiniterainbow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infiniterainbow.adapter.ColorsListAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val initListSize = 50
    private val incrementalAddition = 25
    private lateinit var rvColorsList: RecyclerView
    private lateinit var rvAdapter: ColorsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        setupRecyclerView()
        setupOnScrollListener()
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
}