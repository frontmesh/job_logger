package com.frontmatic.sendwrite.ui.screen.job

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import com.kedia.ogparser.OpenGraphCacheProvider
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

sealed class OpenGraphUrlState {
    object Idle : OpenGraphUrlState()
    object Loading : OpenGraphUrlState()
    data class Success(val result: OpenGraphResult) : OpenGraphUrlState()
    data class Error(val message: String) : OpenGraphUrlState()
}

@HiltViewModel
class AddJobViewModel @Inject constructor(
    private val jobDataSource: JobLocalDataSource,
    private val jobEventLocalDataSource: JobEventLocalDataSource,
    private val cacheProvider: OpenGraphCacheProvider
): ViewModel(), OpenGraphCallback {

    var openGraphUrlState by mutableStateOf<OpenGraphUrlState>(OpenGraphUrlState.Idle)

    fun saveJob(job: Job?, date: Long? = null) {
        if (job == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val jobId = jobDataSource.saveJob(job)

            if (date != null) {
                val jobEvent = JobEvent(
                    type = JobEventType.APPLIED,
                    date = Date(date),
                    name = "Job Application",
                    description = "",
                    jobId = jobId,
                    url = null,
                )

                jobEventLocalDataSource.saveJobEvent(jobEvent)
            }
        }
    }

    fun parseUrl(url: String) {
        openGraphUrlState = OpenGraphUrlState.Loading
        Aptabase.instance.trackEvent("OpenGraphUrl", mapOf("url" to url))
        openGraphParser.parse(url)
    }

    private val openGraphParser by lazy {
        OpenGraphParser(
            listener = this,
            showNullOnEmpty = true,
            cacheProvider = cacheProvider
        )
    }

    override fun onError(error: String) {
        openGraphUrlState = OpenGraphUrlState.Error(error)
        Aptabase.instance.trackEvent("OpenGraphError", mapOf("message" to error))
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        openGraphUrlState = OpenGraphUrlState.Success(openGraphResult)
    }

}