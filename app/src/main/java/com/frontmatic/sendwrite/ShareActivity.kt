package com.frontmatic.sendwrite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.frontmatic.sendwrite.ui.screen.job.AddJobScreen
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
    }

    private val openGraphParser by lazy {
        OpenGraphParser(
            listener = this,
            showNullOnEmpty = true,
            cacheProvider = OpenGraphCacheProvider(this)
        )
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        initCompose(openGraphResult)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun initCompose(job: OpenGraphResult = OpenGraphResult()) {
        setContent {
            val windowSize = calculateWindowSizeClass(this).widthSizeClass
            val isExpandedScreen = windowSize == WindowWidthSizeClass.Expanded

            SendWriteTheme {
                AddJobScreen(
                    intentJob = job,
                )
            }
        }
    }

    override fun onError(error: String) {
        Log.e("ERROR TAG!!!!", "$error")
    }

    private fun initIntent() {
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            if ("text/plain" == intent.type) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
                    Log.d("ShareActivity", "sharedText: $sharedText")
                    // Handle the share d text (LinkedIn job posting)
                    // Here, you can parse and process the shared content as needed.
                    val url = extractUrl(sharedText)
                    Log.d("ShareActivity", "url: $url")
                    if (url != null) {
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