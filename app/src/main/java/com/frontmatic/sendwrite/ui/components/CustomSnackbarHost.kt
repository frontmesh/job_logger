package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.Shape
import kotlinx.coroutines.launch

@Composable
fun CustomSnackbarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = hostState,
        snackbar = { data ->
            Card(
                shape = Shape.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .padding(PAGE_PADDING)
                    .wrapContentSize()
            ) {
                Row(
                    modifier = Modifier.padding(PAGE_PADDING),
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification icon",
                        tint = MaterialTheme.colorScheme.primary,

                    )
                    Text(
                        text = data.visuals.message,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSnackbarHostPreview() {
    val hostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    CustomSnackbarHost(hostState = hostState)

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            hostState.showSnackbar("Message")
        }
    }

}