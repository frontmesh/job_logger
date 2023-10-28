package com.frontmatic.sendwrite.data

enum class AppTheme {
    SYSTEM,
//    DYNAMIC,
    LIGHT_ORANGE,
    LIGHT_GREEN,
    LIGHT_BLUE,
    LIGHT_PURPLE,
    DARK_ORANGE,
    DARK_GREEN,
    DARK_BLUE,
    DARK_PURPLE;

    fun getLocalizedString(): String {
        return when (this) {
            SYSTEM -> "System"
//            DYNAMIC -> "Dynamic"
            LIGHT_GREEN -> "Light Green"
            LIGHT_ORANGE -> "Light Orange"
            LIGHT_BLUE -> "Light Blue"
            LIGHT_PURPLE -> "Light Purple"
            DARK_GREEN -> "Dark Green"
            DARK_ORANGE -> "Dark Orange"
            DARK_BLUE -> "Dark Blue"
            DARK_PURPLE -> "Dark Purple"

        }
    }
}

val appThemes = AppTheme.values().toList()