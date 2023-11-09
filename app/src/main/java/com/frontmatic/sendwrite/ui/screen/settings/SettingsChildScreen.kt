package com.frontmatic.sendwrite.ui.screen.settings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.components.WebView
import com.frontmatic.sendwrite.ui.screen.settings.components.NotificationsSection
import com.frontmatic.sendwrite.ui.screen.settings.components.ThemesSection
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.ramcosta.composedestinations.annotation.Destination

@RequiresApi(Build.VERSION_CODES.O)
@Destination(
    navArgsDelegate = SettingsChildScreenNavArgs::class,
    style = DefaultTransitions::class
)
@Composable
fun SettingsChildScreen(
    navArgs: SettingsChildScreenNavArgs,
    viewModel: SettingsChildViewModel = hiltViewModel()
) {
    val appTheme: AppTheme by viewModel.appTheme
        .collectAsState(AppTheme.LIGHT_GREEN)

    val isJobEventNotificationEnabled: Boolean by viewModel.isJobEventNotificationEnabled
        .collectAsState()

    val isDailyTipsNotificationEnabled: Boolean by viewModel.isDailyTipsNotificationEnabled
        .collectAsState()

    when (navArgs.page) {
        ChildPage.THEMES ->
            ThemesSection(
                appTheme = appTheme,
                onAppThemeSelected = viewModel::updateAppTheme
            )

        ChildPage.NOTIFICATIONS -> NotificationsSection(
            isJobEventNotificationEnabled = isJobEventNotificationEnabled,
            isDailyTipsNotificationEnabled = isDailyTipsNotificationEnabled,
            updateIsJobEventNotificationEnabled = viewModel::updateIsJobEventNotificationEnabled,
            updateIsDailyTipsNotificationEnabled = viewModel::updateIsDailyTipsNotificationEnabled,
        )
        ChildPage.WEBSITE -> WebView(url = "https://www.jobops.app/")
        else -> {}
    }
}