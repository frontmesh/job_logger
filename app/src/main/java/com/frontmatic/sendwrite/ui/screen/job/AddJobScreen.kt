package com.frontmatic.sendwrite.ui.screen.job

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.frontmatic.sendwrite.MainActivity
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.ui.components.OptionData
import com.frontmatic.sendwrite.ui.components.OptionRow
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.kedia.ogparser.OpenGraphResult
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Destination(
    route = "add-job",
)
@Composable
fun AddJobScreen(
    modifier: Modifier = Modifier,
    intentJob: OpenGraphResult?,
    model: AddJobViewModel = hiltViewModel(),
) {
    var job by remember { mutableStateOf(intentJob) }
    var applied by remember { mutableStateOf(false) }

    val topBarTitle = job?.title?.takeIf {
        it.isNotBlank() }?.let { stringResource(R.string.add_a_job_var, it)
    } ?: stringResource(R.string.add_a_job)

    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle!!,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .drawBehind {
                        val borderSize = 1.dp.toPx()

                        drawLine(
                            color = primaryContainerColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = borderSize
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                Button(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    onClick = {
                        job?.let { model.saveJob(it.toJob(applied)) }
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                ) {
                    Row(

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Save",
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    ) { it ->
        Surface(modifier = modifier.padding(it)) {
            Column(modifier = modifier
                .padding(PAGE_PADDING)
            ) {

                JobCard(
                    title = job?.title ?: "",
                    description = job?.description ?: "",
                    imageUrl = job?.image ?: "",
                    isWorkshopStarred = false,
                    starPainterResource = Icons.Outlined.Star
                )

                OptionRow(
                    setting = OptionData(
                        "Applied",
                        "applied",
                        false
                    )
                ) { value -> applied = value }
            }

        }
    }
}


@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageUrl: String,
    isWorkshopStarred: Boolean = false,
    starPainterResource: ImageVector = Icons.Outlined.Star
) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(5)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {

        CoilAsyncImage(
            imageUrl = imageUrl,
            imageShape = RoundedCornerShape(5),
            imageSize = 42.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp),
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp,),
                fontSize = 14.sp
            )

            if (isWorkshopStarred) {
                Icon(
                    imageVector = starPainterResource,
                    modifier = modifier.size(18.dp),
                    contentDescription = "star",
                    tint = Color(0xFFFFEB3B)
                )
            }
        }
    }
}


@Composable
fun CoilAsyncImage(
    imageUrl: String = "https://www.mindinventory.com/blog/wp-content/uploads/2018/05/android-jetpack.jpg",
    imageShape: Shape,
    imageSize: Dp
) {
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
//            .data(ByteBuffer.wrap(imageUrl.toByteArray()))
            .decoderFactory(SvgDecoder.Factory())
            .decoderDispatcher(Dispatchers.IO)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        imageLoader = imageLoader,
        modifier = Modifier
            .clip(shape = imageShape)
            .size(imageSize)
    )
}

@Preview
@Composable
fun AddJobScreenPreview() {
    AddJobScreen(
        intentJob = OpenGraphResult(
            title = "Docklab hiring Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands | LinkedIn",
            image = "https://nl.linkedin.com/jobs/view/tech-lead-hydrotwin-at-docklab-3689393398",
            description = "Posted 2 weeks ago. Docklab is looking for a Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands. Apply…See this and similar jobs on LinkedIn.",
        )
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddJobScreenDarkPreview() {
    AddJobScreen(
        intentJob = OpenGraphResult(
            title = "Docklab hiring Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands | LinkedIn",
            image = "https://nl.linkedin.com/jobs/view/tech-lead-hydrotwin-at-docklab-3689393398",
            description = "Posted 2 weeks ago. Docklab is looking for a Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands. Apply…See this and similar jobs on LinkedIn.",
        )
    )
}

@Preview
@Composable
fun JobCardPreview() {
    JobCard(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 16.dp),
        title = "title",
        description = "description",
        imageUrl = "https://www.mindinventory.com/blog/wp-content/uploads/2018/05/android-jetpack.jpg",
        isWorkshopStarred = false,
        starPainterResource = Icons.Outlined.Star
    )
}