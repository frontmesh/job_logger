package com.frontmatic.sendwrite.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

@Immutable
data class CustomTheme(
    val iconTint: Color = Color.Unspecified,
    val iconSurface : Color = Color.Unspecified
)

val LocalCustomTheme = staticCompositionLocalOf { CustomTheme() }