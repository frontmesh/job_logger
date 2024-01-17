package com.frontmatic.sendwrite.ui.screen.onboarding.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.R

@Composable
fun CompanyLogoAnimation() {
    val yOffset = 11.dp
    val xOffset = 15.dp
    val offsetTransition = rememberInfiniteTransition(label = "")
    val sizeTransition = rememberInfiniteTransition(label = "")

    val iconSize = 35.dp
    val iconSizeOffset = 15.dp

    val offsetAnim by offsetTransition.animateValue(
        initialValue = 0.dp,
        targetValue = 40.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val sizeAnim by sizeTransition.animateValue(
        initialValue = iconSize,
        targetValue = iconSize + iconSizeOffset,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Image(
        painter = painterResource(id = R.drawable.apple),
        contentDescription = "Apple Logo",
        contentScale = ContentScale.FillBounds,
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(25.dp + xOffset + offsetAnim, 145.dp + yOffset),
    )
    Image(
        painter = painterResource(id = R.drawable.linkedin),
        contentDescription = "LinkedIn Logo",
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(95.dp + xOffset, 50.dp + yOffset + offsetAnim),
    )

    Image(
        painter = painterResource(id = R.drawable.amazon),
        contentDescription = "Amazon Logo",
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(175.dp + xOffset, 50.dp + yOffset + offsetAnim),
    )

    Image(
        painter = painterResource(id = R.drawable.google),
        contentDescription = "Google Logo",
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(245.dp + xOffset - offsetAnim, 145.dp + yOffset),
    )

    Icon(
        painter = painterResource(id = R.drawable.indeed),
        contentDescription = "Indeed Logo",
        tint = Color(0xFF003A9B),
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(95.dp + xOffset, 260.dp + yOffset - offsetAnim)
    )

    Icon(
        painter = painterResource(id = R.drawable.glassdoor),
        contentDescription = "Glassdoor Logo",
        tint = Color(0xFF0F9D58),
        modifier = androidx.compose.ui.Modifier
            .size(sizeAnim)
            .offset(175.dp + xOffset, 260.dp + yOffset - offsetAnim)
    )
}