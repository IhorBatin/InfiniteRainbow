package com.example.infiniterainbow.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.infiniterainbow.R
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel
import com.example.infiniterainbow.util.getHexString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorCard(
    modifier: Modifier = Modifier,
    color: Int,
    viewModel: RainbowViewModel,
    onCardClick: ((Int) -> Unit)? = null,
    showFavoriteIcon: Boolean = false,
    showHexOverlay: Boolean = false
) {
    val composeColor = Color(color)
    val contentColor = if (composeColor.luminance() > 0.5f) Color.Black else Color.White
    val favorites by viewModel.favoriteColors.collectAsState()
    val isFavorite = color in favorites

    Card(
        backgroundColor = composeColor,
        modifier = modifier.then(
            if (onCardClick != null) Modifier.clickable { onCardClick(color) } else Modifier
        ),
        shape = RoundedCornerShape(16),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // HEX Overlay in the center
            if (showHexOverlay) {
                Text(
                    text = getHexString(color),
                    color = contentColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Icon on the top right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                if (showFavoriteIcon) {
                    IconButton(
                        onClick = { viewModel.toggleFavorite(color) }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty
                            ),
                            contentDescription = "save color value",
                            tint = contentColor
                        )
                    }
                }
            }
        }
    }
}
