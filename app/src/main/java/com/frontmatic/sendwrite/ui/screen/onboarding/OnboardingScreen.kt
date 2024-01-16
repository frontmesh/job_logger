package com.frontmatic.sendwrite.ui.screen.onboarding

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.frontmatic.sendwrite.R
import kotlinx.coroutines.launch

val titles = listOf(
    "Share and add jobs links with ease",
    "Get notifications about important dates",
    "Follow a job application timeline"
)

val descriptions = listOf(
    "Share a desired job link from any mobile app or a website by pressing a share button and select JobOps app to handle the rest.",
    "Allow notifications, add interview and assignment due dates for a desired job that you are tracking to get notified.",
    "See a timeline view of each job application event you have logged for overall effortless tracking."
)

val animations = listOf(R.raw.share_animation, R.raw.notifcation_animation, R.raw.timeline_animation)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen() {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { titles.size },
    )

    Column(
        modifier = Modifier
            .padding(bottom = 35.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(
            state = pagerState,
        ) { currentPage ->
            
            val textOpacity = animateFloatAsState(
                targetValue = if (currentPage == pagerState.currentPage) 1f else 0f,
                animationSpec = TweenSpec(durationMillis = 500),
                label = ""
            ).value

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animations[currentPage]))

                val dynamicProperties = rememberLottieDynamicProperties(
                    rememberLottieDynamicProperty(
                        property = LottieProperty.COLOR,
                        value = MaterialTheme.colorScheme.primary.toArgb(),
                        keyPath = arrayOf(
                            "**"
                        ),
                    ),
                    rememberLottieDynamicProperty(
                        property = LottieProperty.COLOR,
                        value = MaterialTheme.colorScheme.primary.toArgb(),
                        keyPath = arrayOf(
                            "Stroke 1"
                        )
                    )
                )

                LottieAnimation(
                    modifier = Modifier
                        .size(400.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    dynamicProperties = dynamicProperties
                )

                
                Text(
                    text = titles[currentPage],
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .alpha(textOpacity)
                )
                Text(
                    text = descriptions[currentPage],
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .alpha(textOpacity)
                )
            }
        }
    }

    OnboardingButtons(
        pagerState = pagerState,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingButtons(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {

        PageIndicator(
            pageCount = titles.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (pagerState.currentPage > 0) {
            TextButton(
                modifier = Modifier.align(Alignment.BottomStart),
                onClick = { scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                } }
            ) { Text(text = "Back") }
        }


        if (!isLastPage) {
            TextButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            ) {
                Text(text = "Next")
            }
        } else {
            OutlinedButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Get Started")
            }
        }
    }
}


@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        repeat(pageCount) {
            IndicatorDot(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDot(isSelected: Boolean) {
    val width by animateDpAsState(
        targetValue = if (isSelected) 35.dp else 15.dp,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = TweenSpec<Float>().easing
        ),
        label = ""
    )
    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(15.dp)
            .width(width)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
    )
}


@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}
