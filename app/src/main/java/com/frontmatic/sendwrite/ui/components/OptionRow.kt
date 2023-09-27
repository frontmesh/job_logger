package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.theme.MediumRadius

data class OptionData(val title : String, val name: String, val isChecked : Boolean)

@Composable
fun OptionRow(setting: OptionData, onToggle: (Boolean) -> Unit) {
    var switchState by remember { mutableStateOf(setting.isChecked) }

    fun onToggle() {
        switchState = !switchState
        onToggle(switchState)
    }

    Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(size = MediumRadius)
                )
                .clip(RoundedCornerShape(size = MediumRadius))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onToggle() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = setting.title, fontWeight = FontWeight.Bold)
            Switch(
                checked = switchState,
                onCheckedChange = { onToggle() },
            )
        }
    }
}