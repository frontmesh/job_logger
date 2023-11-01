package com.frontmatic.sendwrite

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.screen.job.AddJobScreen
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val appTheme = viewModel.appTheme.collectAsState(initial = AppTheme.SYSTEM)
            SendWriteTheme(
                appTheme = appTheme.value,
            ) {
                AddJobScreen()
            }
        }
    }
}