package com.frontmatic.sendwrite.ui.screen.settings

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AndroidUriHandler
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.BuildConfig
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ui.components.FancyIcon
import com.frontmatic.sendwrite.ui.components.domain.SectionTitle
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsChildScreenDestination
import com.frontmatic.sendwrite.ui.screen.settings.data.ChildPage
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.frontmatic.sendwrite.ui.theme.AlphaDisabled
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.Shape
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SettingsOption(
    val name: String,
    val icon: ImageVector,
    val clickHandler: () -> Unit = {},
    val childPage: ChildPage
)

fun setOptions(
    navigator: DestinationsNavigator,
    localUriHandler: AndroidUriHandler
): List<SettingsOption> {
    return listOf(
        SettingsOption(
            name = "Select a theme",
            icon = Icons.Filled.Window,
            clickHandler = {
                navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.THEMES
                    )
                )
            },
            childPage = ChildPage.THEMES
        ),
        SettingsOption(
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
        SettingsOption(
            name = "Stats",
            icon = Icons.Filled.QueryStats,
            clickHandler = {
                navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.STATS
                    )
                )
            },
            childPage = ChildPage.STATS
        ),

        SettingsOption(
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
        SettingsOption(
            name = "Privacy Policy",
            icon = Icons.Filled.Fingerprint,
            clickHandler = {
                navigator.navigate(
                    SettingsChildScreenDestination(
                        ChildPage.PRIVACY
                    )
                )
            },
            childPage = ChildPage.PRIVACY
        ),
//        SettingsOption(
//            name = "Terms of Use",
//            icon = Icons.Filled.PrivacyTip,
//            clickHandler = {},
//            childPage = ChildPage.NONE
//        ),
        SettingsOption(
            name = "Rate the app",
            icon = Icons.Filled.Star,
            clickHandler = {
                localUriHandler.openUri("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            },
            childPage = ChildPage.NONE
        ),
//        SettingsOption(
//            name = "Data export/import",
//            icon = Icons.Default.DataObject,
//            clickHandler = {},
//            childPage = ChildPage.NONE
//        ),
    )
}

@Destination(
    style = DefaultTransitions::class,
)
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
//    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity

    val reviewManager = remember {
        ReviewManagerFactory.create(context)
    }

    val reviewInfo = rememberReviewTask(reviewManager = reviewManager)

    val options = remember {
        setOptions(
            navigator = navigator,
            localUriHandler = AndroidUriHandler(context),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = PAGE_PADDING)
    ) {
        OptionsSection(title = "Functional", options = options.take(3))
        OptionsSection(title = "Other", options = options.drop(3))
        AppVersion()
    }
}

@Composable
fun OptionsSection(
    title: String,
    options: List<SettingsOption>
) {
    SectionTitle(text = title)

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = Shape.medium,
        modifier = Modifier
            .shadow(elevation = 2.dp, clip = true, shape = Shape.medium)
    ) {
        LazyColumn {
            itemsIndexed(options) { index, option ->
                OptionRow(
                    option = option,
                    onClick = option.clickHandler
                )
                if (index < options.size - 1) {
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = AlphaDisabled)
                    )
                }
            }
        }
    }
}

@Composable
fun OptionRow(
    modifier: Modifier = Modifier,
    option: SettingsOption,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FancyIcon(
                icon = option.icon,
                contentDescription = null,
                iconColor = MaterialTheme.colorScheme.secondary,
                circleColor = MaterialTheme.colorScheme.background,
                outerShape = CircleShape
            )
            Text(
                text = option.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
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
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun rememberReviewTask(reviewManager: ReviewManager): ReviewInfo? {
    var reviewInfo: ReviewInfo? by remember {
        mutableStateOf(null)
    }
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewInfo = it.result
        }
    }

    return reviewInfo
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
        Icon(
            painter = painterResource(id = R.drawable.jobops_icon),
            contentDescription = "Application Logo",
            modifier = Modifier.size(108.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.app_name) + " " + BuildConfig.VERSION_NAME,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
        )

        Text(text = "© 2023 PropTech Development", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
    }

}

