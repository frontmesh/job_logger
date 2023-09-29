package com.frontmatic.sendwrite.ui.screen.job

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = JobDetailsScreenNavArgs::class,
)
@Composable
fun JobDetailsScreen(
    viewModel: JobDetailsViewModel = hiltViewModel()
) {
    val job by viewModel.job.collectAsState(null)

    Column(
        modifier = Modifier.padding(PAGE_PADDING),
    ) {

        val shape = RoundedCornerShape(size = MediumRadius)
        Surface(
            modifier = Modifier
                .clip(shape)
                .clickable(onClick = { /*TODO*/ }),
            shape = shape,
            color = MaterialTheme.colorScheme.primaryContainer

        ) {
            Row(
                modifier = Modifier.padding(PAGE_PADDING)
            ) {
                Text(
                    text = job?.description ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }

}