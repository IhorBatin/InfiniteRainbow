package com.example.infiniterainbow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infiniterainbow.presentation.components.ColorCard
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel

@Composable
fun ColorListScreen(
    viewModel: RainbowViewModel = viewModel(),
    onCardClick: (Int) -> Unit,
    onCopyClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val colorsList = viewModel.colorsList

    val isAtEnd = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= colorsList.size - 5
        }
    }

    LaunchedEffect(isAtEnd.value) {
        if (isAtEnd.value) {
            viewModel.loadMoreColors()
        }
    }

    val backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    LazyColumn(
        state = listState,
        contentPadding = WindowInsets.systemBars.asPaddingValues(),
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        items(colorsList) { colorInt ->
            ColorCard(
                color = colorInt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(70.dp),
                onCardClick = onCardClick,
                onCopyClick = onCopyClick
            )
        }
    }
}

@Preview
@Composable
fun ColorListScreenPreview() {
    ColorListScreen(onCardClick = {}, onCopyClick = {})
}
