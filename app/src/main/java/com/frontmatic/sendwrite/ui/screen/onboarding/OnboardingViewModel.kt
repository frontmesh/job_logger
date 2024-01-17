package com.frontmatic.sendwrite.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.source.DataStoreSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataSource : DataStoreSource
): ViewModel() {

    fun completeOnboarding () {
        viewModelScope.launch {
            dataSource.updateIsOnboardingCompleted(true)
        }
    }
}