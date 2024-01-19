package com.frontmatic.sendwrite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.screen.job.AddJobIntentScreen
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.kedia.ogparser.OpenGraphCacheProvider
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class ShareActivity : ComponentActivity(), OpenGraphCallback {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aptabase.instance.initialize(this, "A-EU-9748382829");
        initIntent()
    }

    private val openGraphParser by lazy {
        OpenGraphParser(
            listener = this,
            showNullOnEmpty = true,
            cacheProvider = OpenGraphCacheProvider(this)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        initCompose(openGraphResult)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initCompose(job: OpenGraphResult = OpenGraphResult()) {
        setContent {
            val windowSize = calculateWindowSizeClass(this).widthSizeClass
            // TODO: Look into this when creating a tablet version
//            val isExpandedScreen = windowSize == WindowWidthSizeClass.Expanded
            val appTheme = viewModel.appTheme.collectAsState(initial = AppTheme.SYSTEM)

            SendWriteTheme(
                appTheme = appTheme.value,
            ) {
                AddJobIntentScreen(
                    intentJob = job,
                )
            }
        }
    }

    override fun onError(error: String) {
        Aptabase.instance.trackEvent("OpenGraphError", mapOf("message" to error))
    }

    private fun initIntent() {
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            if ("text/plain" == intent.type) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
//                    Log.d("ShareActivity", "sharedText: $sharedText")
                    // Handle the share d text (LinkedIn job posting)
                    // Here, you can parse and process the shared content as needed.
                    val url = extractUrl(sharedText)
//                    Log.d("ShareActivity", "url: $url")
                    if (url != null) {
                        Aptabase.instance.trackEvent("ShareActivity", mapOf("OpenGraphUrlAdded" to url))
                        openGraphParser.parse(url)
                    }
                }
            }
        }
    }

    private fun extractUrl(input: String): String? {
        val pattern: Pattern = Pattern.compile("https?://\\S+")
        val matcher: Matcher = pattern.matcher(input)

        return if (matcher.find()) {
            matcher.group(0) // The first match is the URL
        } else {
            null // No URL found in the input string
        }
    }


}