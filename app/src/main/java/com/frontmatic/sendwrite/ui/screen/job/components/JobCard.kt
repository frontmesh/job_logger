package com.frontmatic.sendwrite.ui.screen.job.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontmatic.sendwrite.ui.components.CoilAsyncImage
import com.frontmatic.sendwrite.ui.theme.MediumRadius


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
            imageShape = RoundedCornerShape(MediumRadius),
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
                    .padding(start = 16.dp),
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