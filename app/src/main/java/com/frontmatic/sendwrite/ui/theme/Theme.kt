package com.frontmatic.sendwrite.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.frontmatic.sendwrite.data.AppTheme


val LightGreenColorScheme = com.frontmatic.sendwrite.ui.theme.green.LightColors
val DarkGreenColorScheme = com.frontmatic.sendwrite.ui.theme.green.DarkColors
val LightBlueColorScheme = com.frontmatic.sendwrite.ui.theme.blue.LightColors
val DarkBlueColorScheme = com.frontmatic.sendwrite.ui.theme.blue.DarkColors

val DarkPurpleColorScheme = darkColorScheme(
    primary = DarkPurplePrimary,
    secondary = DarkPurpleSecondary,
    tertiary = DarkPurpleTertiary,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
)


val LightOrangeColorScheme = lightColorScheme(
    primary = LightOrangePrimary,
    primaryContainer = LightOrangePrimaryContainer,
    secondary = LightOrangeSecondary,
    secondaryContainer = LightOrangeSecondaryContainer,
    tertiary = LightOrangeTertiary,
    tertiaryContainer = LightOrangeTertiaryContainer,
    surface = LightOrangePrimaryContainer,
    onSurface = Color.DarkGray,
)


val DarkOrangeColorScheme = darkColorScheme(
    primary = DarkOrangePrimary,
    onPrimary = DarkOrangeOnPrimary,
    secondary = DarkOrangeSecondary,
    tertiary = DarkOrangeTertiary,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
    primaryContainer = Color.DarkGray,
)



val LightPurpleColorScheme = lightColorScheme(
    primary = LightPurplePrimary,
    primaryContainer = LightPurplePrimaryContainer,
    secondary = LightPurpleSecondary,
    tertiary = LightPurpleTertiary,
    surface = LightPurpleSurface,
    onSurface = LightPurplePrimary,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun SendWriteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    appTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        appTheme == AppTheme.LIGHT_ORANGE -> LightOrangeColorScheme
        appTheme == AppTheme.DARK_ORANGE -> DarkOrangeColorScheme
        appTheme == AppTheme.LIGHT_GREEN -> LightGreenColorScheme
        appTheme == AppTheme.DARK_GREEN  -> DarkGreenColorScheme
        appTheme == AppTheme.LIGHT_PURPLE -> LightPurpleColorScheme
        appTheme == AppTheme.DARK_PURPLE -> DarkPurpleColorScheme
        appTheme == AppTheme.LIGHT_BLUE -> LightBlueColorScheme
        appTheme == AppTheme.DARK_BLUE -> DarkBlueColorScheme
        darkTheme -> DarkGreenColorScheme

        else -> LightGreenColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
