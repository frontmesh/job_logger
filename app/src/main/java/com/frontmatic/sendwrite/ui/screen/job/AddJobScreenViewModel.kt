package com.frontmatic.sendwrite.ui.screen.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.UseCases
import com.frontmatic.sendwrite.local.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddJobScreenViewModel @Inject constructor(
    private val useCases: UseCases,
): ViewModel() {
    fun saveJob(job: Job) {
        viewModelScope.launch {
            useCases.saveJobUseCase(job)
        }
    }

}