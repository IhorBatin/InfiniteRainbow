package com.example.infiniterainbow.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ColorCard(
    color: Int = 55,
    onClick: (Int) -> Unit,
) {
    TextButton( // TODO: Temporarily moved whole card onto button,
        // When issue with card clicks is resolved move back to using just card
        onClick = { onClick(color) },
        modifier = Modifier.wrapContentSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(16),
            backgroundColor = Color(color)
        ) {
        }
    }
}