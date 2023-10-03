package com.frontmatic.sendwrite.ui.components.multifab

import androidx.compose.ui.graphics.vector.ImageVector


/**
 * @param id Cannot be duplicated with the [id] value of another [MultiFabItem].
 */
data class MultiFabItem(
    val id: Int,
    val icon: ImageVector,
    val label: String = ""
)
