package com.frontmatic.sendwrite.ui.screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.frontmatic.sendwrite.ext.findActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

@Composable
fun HomeScreen() {
//    val context = LocalContext.current
//    val activity = context.findActivity()
//    val intent = activity?.intent

//    if (intent != null) {
//        handleIntent(intent)
//    }

    Text(
        text = "HomeScreen",
        modifier = Modifier.fillMaxSize()
    )
}

//fun handleIntent(intent: Intent) {
//    if (Intent.ACTION_SEND == intent.action && intent.type != null) {
//        if ("text/plain" == intent.type) {
//            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
//            if (sharedText != null) {
//                Log.d("HomeScreen", "sharedText: $sharedText")
//                val url = extractUrls(sharedText).first()
//                Log.d("HomeScreen", "url: $url")
//                // Handle the share d text (LinkedIn job posting)
//                // Here, you can parse and process the shared content as needed.
//            }
//        }
//    }
//}

fun extractUrls(input: String): List<String> {
    val urlPattern: Pattern = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    val matcher: Matcher = urlPattern.matcher(input)
    val urls = mutableListOf<String>()

    while (matcher.find()) {
        val url = matcher.group()
        urls.add(url)
    }

    return urls
}
