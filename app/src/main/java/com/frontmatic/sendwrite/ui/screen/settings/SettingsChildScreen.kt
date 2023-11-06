package com.frontmatic.sendwrite.ui.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.screen.settings.components.ThemesSection
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.ramcosta.composedestinations.annotation.Destination

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

    when (navArgs.page) {
        ChildPage.THEMES -> {
            ThemesSection(
                appTheme = appTheme,
                onAppThemeSelected = viewModel::updateAppTheme
            )
        }

        else -> {}
    }
}