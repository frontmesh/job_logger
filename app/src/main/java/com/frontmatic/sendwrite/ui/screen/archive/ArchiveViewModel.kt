package com.frontmatic.sendwrite.ui.screen.archive

import androidx.lifecycle.ViewModel
import com.frontmatic.sendwrite.local.source.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject() constructor(
    private val localDataSource: LocalDataSource
): ViewModel() {
}