package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.infiniterainbow.R
import com.example.infiniterainbow.presentation.components.ColorCard
import com.example.infiniterainbow.presentation.util.PaletteImageHelper
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel

@Composable
fun PaletteScreen(
    colorInt: Int,
    viewModel: RainbowViewModel,
    colorName: String = stringResource(id = R.string.color),
    onCopyClick: (Int) -> Unit,
    onCardClick: (Int) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(colorInt) {
        viewModel.initPalette(colorInt)
    }

    val palette = viewModel.palette ?: return
    val favorites by viewModel.favoriteColors.collectAsState()
    val isFavorite = colorInt in favorites

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 50.dp)
        ) {
            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Share Button
                IconButton(
                    onClick = { PaletteImageHelper.sharePalette(context, palette, colorName) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share palette",
                        tint = textColor
                    )
                }

                // Copy Button
                IconButton(
                    onClick = { onCopyClick(colorInt) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = "Copy color value",
                        tint = textColor
                    )
                }
                
                // Favorite Button
                IconButton(
                    onClick = { viewModel.toggleFavorite(colorInt) }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty
                        ),
                        contentDescription = "Toggle favorite",
                        tint = textColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(18.dp))

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
                viewModel = viewModel,
                showHexOverlay = false
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
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                    ColorCard(
                        color = palette.similar[1],
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(8.dp),
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    ColorCard(
                        color = palette.similar[2],
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(8.dp),
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                    ColorCard(
                        color = palette.similar[3],
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(8.dp),
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    ColorCard(
                        color = palette.similar[4],
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(8.dp),
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                    ColorCard(
                        color = palette.similar[5],
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(8.dp),
                        onCardClick = onCardClick,
                        viewModel = viewModel,
                        showHexOverlay = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                    onCardClick = onCardClick,
                    viewModel = viewModel,
                    showHexOverlay = true
                )
                ColorCard(
                    color = palette.analogous[1],
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(8.dp),
                    onCardClick = onCardClick,
                    viewModel = viewModel,
                    showHexOverlay = true
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
                    .height(120.dp),
                onCardClick = onCardClick,
                viewModel = viewModel,
                showHexOverlay = true
            )

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
