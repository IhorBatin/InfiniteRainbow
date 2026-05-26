package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
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
    onCardClick: (Int) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black

    val favoriteColors by viewModel.favoriteColors.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.clear_favorites)) },
            text = { Text(stringResource(R.string.clear_favorites_confirmation)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearFavorites()
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.clear), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(vertical = 40.dp)
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
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.clear_favorites),
                            tint = textColor
                        )
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = WindowInsets.systemBars.asPaddingValues()
                            .calculateBottomPadding() + 8.dp
                    )
                ) {
                    items(favoriteColors.toList()) { colorInt ->
                        ColorCard(
                            color = colorInt,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .aspectRatio(1f),
                            onCardClick = onCardClick,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
