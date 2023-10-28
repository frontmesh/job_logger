package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING

@Composable
fun ListCell(
    shape: Shape = RoundedCornerShape(size = MediumRadius),
    name: String,
    trailingText: String? = null,
    listCellBackground: Color = MaterialTheme.colorScheme.primaryContainer,
    imageUrl: String? = null,
    icon: ImageVector? = null,
    iconBackground: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {
    // A simple box to draw the growing circle, which emanates from behind the icon
    Box(
        Modifier
            .padding(vertical = 6.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PAGE_PADDING)
                .clip(shape)
                .clickable(onClick = onClick),
            shape = shape,
            color = listCellBackground

        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                shape = CircleShape,
                                color = iconBackground
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUrl != null) {
                            CoilAsyncImage(
                                imageUrl = imageUrl,
                                imageShape = RoundedCornerShape(MediumRadius),
                                imageSize = 42.dp
                            )
                        } else if (icon != null) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(42.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.size(8.dp))

                    if (trailingText != null) {
                        Text(
                            text = trailingText,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = AlphaDisabled)
                        )
                    }
                }

                Row {
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = AlphaDisabled))
                    )
                    Divider(color = MaterialTheme.colorScheme.primary)
                }
            }

        }
    }
}