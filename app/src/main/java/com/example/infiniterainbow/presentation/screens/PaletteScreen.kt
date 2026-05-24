package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infiniterainbow.presentation.components.ColorCard
import com.example.infiniterainbow.util.getAnalogousColors
import com.example.infiniterainbow.util.getComplementaryColor
import com.example.infiniterainbow.util.getSimilarColors

@Composable
fun PaletteScreen(
    colorInt: Int,
    onCopyClick: (Int) -> Unit
) {
    val similarColors = remember(colorInt) { getSimilarColors(colorInt) }
    val analogousColors = remember(colorInt) { getAnalogousColors(colorInt) }
    val complementaryColor = remember(colorInt) { getComplementaryColor(colorInt) }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // 1st section: originally picked color
        ColorCard(
            color = colorInt,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            onCopyClick = onCopyClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2nd section: Similar colors
        Text(
            text = "Similar colors", 
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ColorCard(
                    color = similarColors[0],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCopyClick = onCopyClick
                )
                ColorCard(
                    color = similarColors[1],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCopyClick = onCopyClick
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                ColorCard(
                    color = similarColors[2],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCopyClick = onCopyClick
                )
                ColorCard(
                    color = similarColors[3],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCopyClick = onCopyClick
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Add dynamic degree - / - for user to select
        // 3rd section: Analogous Colors
        Text(
            text = "Analogous Colors", 
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ColorCard(
                color = analogousColors[0],
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .padding(8.dp),
                onCopyClick = onCopyClick
            )
            ColorCard(
                color = analogousColors[1],
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .padding(8.dp),
                onCopyClick = onCopyClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4th section: Complimentary
        Text(
            text = "Complimentary", 
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        ColorCard(
            color = complementaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onCopyClick = onCopyClick
        )
        
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PaletteScreenPreview() {
    PaletteScreen(colorInt = -15681628, onCopyClick = {})
}
