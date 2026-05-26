package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.infiniterainbow.R
import com.example.infiniterainbow.presentation.components.ColorCard
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel

@Composable
fun FavoritesScreen(
    viewModel: RainbowViewModel,
    onCardClick: (Int) -> Unit,
    onCopyIconClick: (Int) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black
    
    val favoriteColors by viewModel.favoriteColors.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        if (favoriteColors.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_favorites_yet),
                    style = MaterialTheme.typography.h6,
                    color = textColor
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = WindowInsets.systemBars.asPaddingValues()
            ) {
                items(favoriteColors.toList()) { colorInt ->
                    ColorCard(
                        color = colorInt,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(70.dp),
                        onCardClick = onCardClick
                    )
                }
            }
        }
    }
}
