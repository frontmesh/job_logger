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

val DarkGreenColorScheme = darkColorScheme(
    primary = Green80,
    secondary = GreenGrey80,
    tertiary = Lime80,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
    primaryContainer = Color.DarkGray,
)

val DarkPurpleColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
)

val DarkOrangeColorScheme = darkColorScheme(
    primary = Orange80,
    secondary = OrangeGrey80,
    tertiary = Coral80,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
    primaryContainer = Color.DarkGray,
)

val DarkBlueColorScheme = darkColorScheme(
    primary = Blue80,
    secondary = BlueGrey80,
    tertiary = Cyan80,
    surface = Color.DarkGray,
    onSurface = Color.LightGray,
)


val LightPurpleColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = Color.LightGray,
    onSurface = Color.DarkGray,

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

val LightGreenColorScheme = lightColorScheme(
    primary = Green40,
    secondary = GreenGrey40,
    primaryContainer = Green80,
    tertiary = Lime40,
    surface = Color.White,
    onSurface = Color.DarkGray,
)

val LightOrangeColorScheme = lightColorScheme(
    primary = Orange40,
    secondary = OrangeGrey40,
    primaryContainer = Orange80,
    tertiary = Coral40,
    surface = Color.White,
    onSurface = Color.DarkGray,
)

val LightBlueColorScheme = lightColorScheme(
    primary = Blue40,
    secondary = BlueGrey40,
    tertiary = Cyan40,
    surface = Color.White,
    onSurface = Color.DarkGray,
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

        appTheme == AppTheme.LIGHT ||
        appTheme == AppTheme.LIGHT_GREEN -> LightGreenColorScheme
        appTheme == AppTheme.DARK_GREEN ||
        appTheme == AppTheme.DARK ||
        darkTheme -> DarkGreenColorScheme
        appTheme == AppTheme.LIGHT_ORANGE -> LightOrangeColorScheme
        appTheme == AppTheme.DARK_ORANGE -> DarkOrangeColorScheme
        appTheme == AppTheme.LIGHT_PURPLE -> LightPurpleColorScheme
        appTheme == AppTheme.DARK_PURPLE -> DarkPurpleColorScheme
        appTheme == AppTheme.LIGHT_BLUE -> LightBlueColorScheme
        appTheme == AppTheme.DARK_BLUE -> DarkBlueColorScheme

        else -> LightGreenColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun DarkTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicDarkColorScheme(context)
        }
        else -> DarkGreenColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun LightTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicLightColorScheme(context)
        }
        else -> LightGreenColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}