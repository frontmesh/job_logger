package com.frontmatic.sendwrite.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.theme.AlphaDisabled
import com.frontmatic.sendwrite.ui.theme.AlphaHigh
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(
    route = "home",
    start = true
)
@Composable
fun HomeScreen(
    model: HomeScreenViewModel = hiltViewModel()
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                JobCell(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    taskCount = "0",
                    title = "Due Today",
                    icon = Icons.Filled.DateRange,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = { /*TODO*/ },
                    isSelected = false
                )
                JobCell(
                    modifier = Modifier
                        .weight(1f),
                    taskCount = "0",
                    title = "Due Tomorrow",
                    icon = Icons.Filled.DateRange,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = { /*TODO*/ },
                    isSelected = false
                )
            }
        }

        item {
            Text(
                text = "All Jobs",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
    }
}


@Composable
private fun JobCell(
    modifier: Modifier,
    taskCount: String,
    title: String,
    icon: ImageVector,
    iconColor: Color,
    iconText: String = "",
    onClick: () -> Unit,
    isSelected: Boolean = false,
) {
    val shape = RoundedCornerShape(size = MediumRadius)
    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = if (isSelected) {
            iconColor
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            shape = CircleShape,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                iconColor
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = if (isSelected) {
                            iconColor
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )

                    if (iconText.isNotBlank()) {
                        Text(
                            text = iconText,
                            fontSize = 8.sp,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(top = 2.dp),
                            color = if (isSelected) {
                                iconColor
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }

                Text(
                    text = taskCount,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(8.dp))

            val alpha = if (isSelected) {
                AlphaHigh
            } else {
                AlphaDisabled
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary.copy(alpha)
            )
        }
    }
}