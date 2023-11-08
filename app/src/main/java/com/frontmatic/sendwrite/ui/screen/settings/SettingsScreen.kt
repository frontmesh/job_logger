package com.frontmatic.sendwrite.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.BuildConfig
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ui.components.FancyIcon
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsChildScreenDestination
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.SectionSurface
import com.frontmatic.sendwrite.ui.theme.Shape
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


enum class ChildPage {
    NONE,
    THEMES,
    NOTIFICATIONS,
    WEBSITE
}
sealed class SettingsOption {
    data class Option(
        val name: String,
        val icon: ImageVector,
        val clickHandler: () -> Unit = {},
        val childPage: ChildPage
    ) : SettingsOption()

    object Divider : SettingsOption()
}

fun setOptions(
    navigator: DestinationsNavigator,
): List<SettingsOption> {
    return listOf(
        SettingsOption.Option(
            name = "Select a theme",
            icon = Icons.Filled.Star,
            clickHandler = {
                navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.THEMES
                    )
                )
            },
            childPage = ChildPage.THEMES
        ),
        SettingsOption.Option(
            name = "Notifications",
            icon = Icons.Filled.Notifications,
            clickHandler = {
                navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.NOTIFICATIONS
                    )
                )
            },
            childPage = ChildPage.NOTIFICATIONS
        ),
        SettingsOption.Divider,
        SettingsOption.Option(
            name = "Website",
            icon = Icons.Filled.Link,
            clickHandler = {
               navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.WEBSITE
                    )
                )
            },
            childPage = ChildPage.WEBSITE
        ),
        SettingsOption.Option(
            name = "Privacy Policy",
            icon = Icons.Filled.Fingerprint,
            clickHandler = {},
            childPage = ChildPage.NONE
        ),
        SettingsOption.Option(
            name = "Terms of Use",
            icon = Icons.Filled.PrivacyTip,
            clickHandler = {},
            childPage = ChildPage.NONE
        ),
        SettingsOption.Option(
            name = "Rate the app",
            icon = Icons.Filled.Star,
            clickHandler = {},
            childPage = ChildPage.NONE
        ),
        SettingsOption.Option(
            name = "Data export/import",
            icon = Icons.Default.DataObject,
            clickHandler = {},
            childPage = ChildPage.NONE
        ),
    )
}

@Destination(
    style = DefaultTransitions::class,
)
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val options = remember {
        setOptions(navigator = navigator)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = PAGE_PADDING)
    ) {
        SectionSurface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = Shape.medium
        ) {
            LazyColumn {
                items(options) { option ->
                    when (option) {
                        is SettingsOption.Option ->
                            OptionRow(
                                option = option,
                                onClick = option.clickHandler
                            )
                        is SettingsOption.Divider -> Divider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            }
        }

        AppVersion()
    }
}

@Composable
fun OptionRow(
    modifier: Modifier = Modifier,
    option: SettingsOption.Option,
    onClick: () -> Unit
) {
    Surface(onClick = onClick) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FancyIcon(
                icon = option.icon,
                contentDescription = null,
                iconColor = MaterialTheme.colorScheme.secondary,
                circleColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = option.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                if (option.childPage != ChildPage.NONE) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
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

        Text(text = "© 2023 PropTech Development", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
    }

}

