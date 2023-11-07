package com.frontmatic.sendwrite.ui.screen.settings.components

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ext.hasNotificationPermission
import com.frontmatic.sendwrite.ui.components.OptionRow
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.Shape

@Composable
fun NotificationsSection(
    isJobEventNotificationEnabled: Boolean,
    isDailyTipsNotificationEnabled: Boolean,
    updateIsJobEventNotificationEnabled: (Boolean) -> Unit,
    updateIsDailyTipsNotificationEnabled: (Boolean, Context) -> Unit
) {
    val context = LocalContext.current

    var isNotificationPermissionGranted by remember {
        mutableStateOf(context.hasNotificationPermission())
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isNotificationPermissionGranted = isGranted
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = PAGE_PADDING)
    ) {

        if (!isNotificationPermissionGranted) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(Shape.medium)
                    .background(MaterialTheme.colorScheme.error)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = "Error Icon",
                    tint = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.notifications_disabled_description),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }

        OptionRow(
            label = "Job Event Notifications",
            enabled = isJobEventNotificationEnabled,
            onToggle = {
                updateIsJobEventNotificationEnabled(it)
            },
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OptionRow(
            label = "Daily tips",
            enabled = isDailyTipsNotificationEnabled,
            onToggle = {
                updateIsDailyTipsNotificationEnabled(it, context)
            }
        )
    }
}