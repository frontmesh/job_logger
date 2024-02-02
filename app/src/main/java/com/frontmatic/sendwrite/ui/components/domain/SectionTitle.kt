package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING

@Composable
fun SectionTitle(
    text: String,
) {
    Row(
        modifier = Modifier
            .padding(all = PAGE_PADDING)
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }

}