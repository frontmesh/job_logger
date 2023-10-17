package com.frontmatic.sendwrite.data

enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK;

    fun getLocalizedString(): String {
        return when (this) {
            SYSTEM -> "System"
            LIGHT -> "Light"
            DARK -> "Dark"
        }
    }
}

val appThemes = listOf(AppTheme.SYSTEM, AppTheme.LIGHT, AppTheme.DARK)