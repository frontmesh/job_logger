package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LetterImagePlaceholder(
    modifier: Modifier = Modifier,
    url: String,
    shape: Shape,
) {
    val letter = url.replaceFirst(Regex("^https?://www\\."), "")
        .firstOrNull()!!.uppercaseChar()

    val color = getColorForLetter(letter)

    Box(
        modifier = modifier
            .size(48.dp)
            .background(color = color, shape = shape)
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = letter.toString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@Composable
private fun getColorForLetter(letter: Char): Color {
    return when (letter) {
        // Amazon orange
        'A', 'J', 'K' -> Color(0xFFFF9800)
        'B', 'F', 'L'  -> Color(0xFF2196F3)
        'C', 'N', 'P' -> Color(0xFF9C27B0)
        'D', 'O', 'R'  -> Color(0xFFE91E63)
        'E', 'M', 'S'   -> Color(0xFF673AB7)
        'G', 'W', 'Y'  -> Color(0xFF607D8B)
        'H', 'U', 'V'  -> Color(0xFF795548)
        'I', 'T', 'X', 'Z', 'Q' -> Color(0xFF8BC34A)
        else -> MaterialTheme.colorScheme.primary
    }
}