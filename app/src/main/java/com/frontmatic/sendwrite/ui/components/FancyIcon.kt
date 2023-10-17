package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme

@Composable
fun FancyIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    iconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {

    Surface(
        modifier = modifier
            .alpha(1f)
            .size(32.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}

@Preview
@Composable
private fun FancyIconPreview() {
    SendWriteTheme {
        FancyIcon(
            icon = Icons.Default.NorthEast,
            contentDescription = "icon"
        )
    }
}