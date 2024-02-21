package com.frontmatic.sendwrite.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun String.cap() = this.lowercase().replaceFirstChar { it.uppercase() }

@Composable
fun <T> T.useDebounce(
    delayMillis: Long = 500L,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onDebounce: (T) -> Unit
): T {
    val state by rememberUpdatedState(newValue = this)
    var job: Job? = null

    DisposableEffect(state) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(delayMillis)
            onDebounce(state)
        }
        onDispose {
            job?.cancel()
        }
    }

    return state
}

