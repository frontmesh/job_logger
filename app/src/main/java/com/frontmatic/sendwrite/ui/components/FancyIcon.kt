package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme

@Composable
fun FancyIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String?,
    iconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    circleColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconSize: Dp = 32.dp,
    outerShape: CornerBasedShape = CircleShape
) {

    Surface(
        modifier = modifier
            .alpha(1f)
            .size(iconSize),
        shape = outerShape,
        color = circleColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier
                .padding(4.dp)
//                .size(iconSize)
        )
    }
}

@Preview
@Composable
private fun FancyIconPreview() {
    SendWriteTheme(
        appTheme = AppTheme.DARK_BLUE
    ) {
        FancyIcon(
            icon = Icons.Default.NorthEast,
            contentDescription = "icon"
        )
    }
}