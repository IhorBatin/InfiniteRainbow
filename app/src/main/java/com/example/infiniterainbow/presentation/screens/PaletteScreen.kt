package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infiniterainbow.R
import com.example.infiniterainbow.presentation.components.ColorCard
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel

@Composable
fun PaletteScreen(
    colorInt: Int,
    viewModel: RainbowViewModel = viewModel(),
    colorName: String = stringResource(id = R.string.color),
    onCopyClick: (Int) -> Unit,
    onCardClick: (Int) -> Unit,
) {
    LaunchedEffect(colorInt) {
        viewModel.initPalette(colorInt)
    }

    val palette = viewModel.palette ?: return

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 60.dp)
    ) {
        // 1st section: originally picked color
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = colorName,
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        ColorCard(
            color = palette.original,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            onCopyClick = onCopyClick
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 2nd section: Similar colors
        Text(
            text = stringResource(id = R.string.similar_colors),
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ColorCard(
                    color = palette.similar[0],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
                ColorCard(
                    color = palette.similar[1],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                ColorCard(
                    color = palette.similar[2],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
                ColorCard(
                    color = palette.similar[3],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                ColorCard(
                    color = palette.similar[4],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
                ColorCard(
                    color = palette.similar[5],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Add dynamic degree - / - for user to select
        // 3rd section: Analogous Colors
        Text(
            text = stringResource(id = R.string.analogous_colors), 
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ColorCard(
                color = palette.analogous[0],
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .padding(8.dp),
                onCardClick = onCardClick
            )
            ColorCard(
                color = palette.analogous[1],
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .padding(8.dp),
                onCardClick = onCardClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4th section: Complimentary
        Text(
            text = stringResource(id = R.string.complimentary),
            style = MaterialTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        ColorCard(
            color = palette.complementary,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onCardClick = onCardClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaletteScreenPreview() {
    PaletteScreen(colorInt = -15681628, colorName = "Sample color", onCopyClick = {}, onCardClick = {})
}
