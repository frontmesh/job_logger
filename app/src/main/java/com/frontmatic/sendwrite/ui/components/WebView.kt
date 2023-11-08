package com.frontmatic.sendwrite.ui.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(url: String) {
    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}