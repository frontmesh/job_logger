package com.frontmatic.sendwrite.util

import android.util.Log
import android.webkit.URLUtil
import java.util.regex.Matcher
import java.util.regex.Pattern

fun isUrlValid(url: String): Boolean {
    val pattern: Pattern = Pattern.compile("https?://\\S+")
    val matcher: Matcher = pattern.matcher(url)

    Log.d("isUrlValid", "isUrlValid: ${matcher.find()}")
    return  URLUtil.isValidUrl(url)
}