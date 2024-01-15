package com.frontmatic.sendwrite

import androidx.lifecycle.ViewModel
import com.frontmatic.sendwrite.local.source.DataStoreSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreSource: DataStoreSource,
): ViewModel() {

    val appTheme = dataStoreSource.appThemeFlow()

    val isOnboardingCompleted = dataStoreSource.isOnboardingCompleted()

}