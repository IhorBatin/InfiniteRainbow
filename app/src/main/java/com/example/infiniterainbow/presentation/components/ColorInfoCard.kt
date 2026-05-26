package com.example.infiniterainbow.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infiniterainbow.R

@Composable
fun ColorCard(
    modifier: Modifier = Modifier,
    color: Int,
    onCardClick: ((Int) -> Unit)? = null,
    onCopyClick: ((Int) -> Unit)? = null
) {
    val composeColor = Color(color)
    val contentColor = if (composeColor.luminance() > 0.5f) Color.Black else Color.White

    Card(
        backgroundColor = composeColor,
        modifier = modifier.then(
            if (onCardClick != null) Modifier.clickable { onCardClick(color) } else Modifier
        ),
        shape = RoundedCornerShape(16),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            if (onCopyClick != null) {
                IconButton(
                    onClick = { onCopyClick(color) },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = "Copy color value",
                        tint = contentColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorCardPreview() {
    ColorCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp),
        color = -15681628,
        onCardClick = {}
    )
}