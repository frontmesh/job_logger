package com.frontmatic.sendwrite.ui.components.multifab

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
interface FabIcon {

    @Stable
    val imageVector: ImageVector

    @Stable
    val iconRotate: Float?
}

private class FabIconImpl(
    override val imageVector: ImageVector,
    override val iconRotate: Float?
) : FabIcon

/**
 * Affects the main fab icon.
 *
 * @param iconRes [MultiFloatingActionButton]'s main icon
 * @param iconRotate if is not null, the [iconRes] rotates as much as [iconRotate] when [MultiFloatingActionButton] is in [MultiFabState.Expand] state.
 */
fun FabIcon(
    iconRotate: Float? = null,
    imageVector: ImageVector,
): FabIcon =
    FabIconImpl( iconRotate = iconRotate, imageVector = imageVector)