package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.frontmatic.sendwrite.R
import kotlinx.coroutines.Dispatchers

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