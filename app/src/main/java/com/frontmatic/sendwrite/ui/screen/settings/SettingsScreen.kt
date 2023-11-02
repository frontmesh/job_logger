package com.frontmatic.sendwrite.ui.screen.settings

import android.media.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.BuildConfig
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.data.appThemes
import com.frontmatic.sendwrite.ui.components.FancyIcon
import com.frontmatic.sendwrite.ui.components.domain.JobListTitle
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.SectionSurface
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.frontmatic.sendwrite.ui.theme.Shape

import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = "settings",
)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val appTheme: AppTheme by viewModel.appTheme
        .collectAsState(AppTheme.LIGHT_GREEN)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = PAGE_PADDING)
    ) {
        JobListTitle(title = "App", padding = 0.dp)
        ThemesSection(
            appTheme = appTheme,
            onAppThemeSelected = {
                viewModel.updateAppTheme(appTheme = it)
            }
        )
        SectionSurface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = Shape.medium
        ) {
            LazyColumn(content = {
                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        text = "General",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    SettingsOption(
                        title = "Website",
                        icon = Icons.Filled.Link,
                        onClick = {}
                    )
                }
                item {
                    SettingsOption(
                        title = "Privacy Policy",
                        icon = Icons.Filled.PrivacyTip,
                        onClick = {}
                    )
                }

                item {
                    SettingsOption(
                        title = "Terms of Use",
                        icon = Icons.Filled.Fingerprint,
                        onClick = {}
                    )
                }

                item {
                    SettingsOption(
                        title = "Rate the app",
                        icon = Icons.Filled.Star,
                        onClick = {}
                    )
                }
                item {
                    SettingsOption(
                        title = "Data export/import",
                        icon = Icons.Default.DataObject,
                        onClick = {}
                    )
                }

                item {
                    AppVersion()
                }
            })
        }
    }
}

@Composable
fun SettingsOption(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FancyIcon(
            icon = icon,
            contentDescription = "Date",
            iconColor = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
private fun ThemesSection(
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


@Composable
fun AppVersion() {

    // on below line we are creating a column
    Column(
        // on below line we are adding a modifier to it
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name) + " " + BuildConfig.VERSION_NAME,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
        )

        Text(text = "© 2023 Frontmatic", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
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
