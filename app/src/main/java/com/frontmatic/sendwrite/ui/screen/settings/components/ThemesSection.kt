package com.frontmatic.sendwrite.ui.screen.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.data.appThemes
import com.frontmatic.sendwrite.ui.components.FancyIcon
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme

@Composable
fun ThemesSection(
    modifier: Modifier = Modifier,
    appTheme: AppTheme,
    onAppThemeSelected: (AppTheme) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            text = "Select a theme",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Themes(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            selectedAppTheme = appTheme,
            onAppThemeSelected = onAppThemeSelected
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Themes(
    modifier: Modifier = Modifier,
    selectedAppTheme: AppTheme,
    onAppThemeSelected: (AppTheme) -> Unit
) {
    val themes = appThemes

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 72.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = themes,
            key = { _: Int, theme: AppTheme -> theme.name }
        ) { _: Int, theme: AppTheme ->
            ThemeButton(
                isSelected = theme == selectedAppTheme,
                appTheme = theme,
                onAppThemeSelected = onAppThemeSelected
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun ThemeButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    appTheme: AppTheme,
    onAppThemeSelected: (AppTheme) -> Unit
) {
    val borderColor: Color by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outlineVariant
        }, label = ""
    )

    val backgroundColor: Color by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }, label = ""
    )

    val textColor: Color by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        }, label = ""
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.27f)
                .clip(RoundedCornerShape(size = 16.dp))
                .clickable { onAppThemeSelected(appTheme) },
            shape = RoundedCornerShape(size = 16.dp),
            border = BorderStroke(width = 2.6.dp, color = borderColor),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (appTheme) {
                    AppTheme.SYSTEM -> {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            SendWriteTheme(appTheme = AppTheme.LIGHT_GREEN) {
                                Row(modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .background(MaterialTheme.colorScheme.surface)) {
                                }
                            }
                            SendWriteTheme(appTheme = AppTheme.DARK_GREEN){
                                Row(modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .background(MaterialTheme.colorScheme.surface)) {
                                }
                            }
                        }
                    }
                    else -> {
                        SendWriteTheme(
                            appTheme = appTheme,
                            dynamicColor = AppTheme.SYSTEM == appTheme
                        ) {
                            Row(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)) {
                            }
                        }
                    }
                }
                Column(modifier = Modifier.align(Alignment.Center)) {
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        FancyIcon(
                            icon = Icons.Filled.Check,
                            contentDescription = "Check icon"
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = appTheme.getLocalizedString(),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}
