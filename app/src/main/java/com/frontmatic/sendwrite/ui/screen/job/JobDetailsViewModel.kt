package com.frontmatic.sendwrite.ui.screen.job

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.UseCases
import com.frontmatic.sendwrite.ui.screen.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject() constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: UseCases
): ViewModel() {

    private val navArgs: JobDetailsScreenNavArgs = savedStateHandle.navArgs()
    val job = useCases.getJobById(navArgs.id)

    fun deleteJob() {
        CoroutineScope(Dispatchers.IO).launch {
            job.collect {
                useCases.deleteJob(it)
            }
        }
    }

}