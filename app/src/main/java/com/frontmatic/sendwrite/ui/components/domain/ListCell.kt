package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.components.CoilAsyncImage
import com.frontmatic.sendwrite.ui.theme.AlphaDisabled
import com.frontmatic.sendwrite.ui.theme.AlphaMedium
import com.frontmatic.sendwrite.ui.theme.ICON_LARGE
import com.frontmatic.sendwrite.ui.theme.LargeRadius
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING

@Composable
fun ListCell(
    modifier: Modifier = Modifier,
    iconShape: Shape = RoundedCornerShape(MediumRadius),
    surfaceShape : Shape = RoundedCornerShape(LargeRadius),
    name: String,
    trailingText: String? = null,
    trailingContent: @Composable () -> Unit = {},
    secondaryContent: @Composable (() -> Unit)? = null,
    imageUrl: String? = null,
    icon: ImageVector? = null,
    iconBackground: Color = MaterialTheme.colorScheme.background,
    onClick: () -> Unit,
) {
    val bottomBorderColor = MaterialTheme.colorScheme.secondary.copy(AlphaMedium)
    // A simple box to draw the growing circle, which emanates from behind the icon

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
//            .drawBehind {
//                val offsetVal = LargeRadius.toPx()
//
//                drawLine(
//                    brush = SolidColor(
//                        value = bottomBorderColor
//                    ),
//                    start = Offset(x = offsetVal, y = this.size.height),
//                    end = Offset(x = this.size.width - offsetVal, y = this.size.height),
//                    strokeWidth = 1.dp.toPx()
//                )
//            }
            .clip(surfaceShape)
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.primary.copy(DividerAlpha),
//                    shape = shape,
//                )
            .clickable(onClick = onClick),
        shape = surfaceShape,
        color = MaterialTheme.colorScheme.surface

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PAGE_PADDING, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .size(ICON_LARGE)
                        .background(
                            shape = iconShape,
                            color = iconBackground,
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = AlphaDisabled),
                            shape = iconShape,
                        )
                        .clip(iconShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUrl != null) {
                        CoilAsyncImage(
                            imageUrl = imageUrl,
                            imageShape = iconShape,
                            imageSize = ICON_LARGE
                        )
                    } else if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(6.dp)
                                .size(ICON_LARGE),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (secondaryContent != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        secondaryContent()
                    }
                }
            }
            Spacer(Modifier.size(8.dp))
            Column {
                if (trailingText != null) {
                    Text(
                        text = trailingText,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = AlphaDisabled)
                    )
                }

                trailingContent()
            }
        }

    }
}