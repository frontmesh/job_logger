package com.frontmatic.sendwrite.ui.screen.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.local.source.DataStoreSource
import com.frontmatic.sendwrite.ui.screen.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsChildViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStoreSource: DataStoreSource,
): ViewModel() {
    val navArgs: SettingsChildScreenNavArgs = savedStateHandle.navArgs()

    val appTheme = dataStoreSource.appThemeFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppTheme.LIGHT_GREEN
    )

    fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            dataStoreSource.updateAppTheme(appTheme = appTheme)
        }
    }

}