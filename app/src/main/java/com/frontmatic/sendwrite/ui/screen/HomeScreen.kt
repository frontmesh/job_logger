package com.frontmatic.sendwrite.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(
    route = "home",
    start = true
)
@Composable
fun HomeScreen() {

    Text(
        text = "HomeScreen",
        modifier = Modifier.fillMaxSize()
    )
}
