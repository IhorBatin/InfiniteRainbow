package com.example.infiniterainbow.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ColorCard(
    color: Int = -15681628,
    onClick: (Int) -> Unit,
) {
    Card(
        backgroundColor = Color(color),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clickable { onClick(color) },
        shape = RoundedCornerShape(16),
        elevation = 4.dp
    ) {
    }
}
