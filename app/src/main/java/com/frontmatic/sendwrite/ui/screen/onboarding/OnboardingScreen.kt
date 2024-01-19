package com.frontmatic.sendwrite.ui.screen.onboarding

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.onboarding.components.CompanyLogoAnimation
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


val titles = listOf(
    "Add job links with ease",
    "Get notified about important dates",
    "Follow a job application timeline"
)

val descriptions = listOf(
    "Share a desired job link from any mobile app or a website by pressing a share button and select JobOps app to handle the rest.",
    "Allow notifications, add interview and assignment due dates for a desired job that you are tracking to get notified.",
    "See a timeline view of each job application event you have logged for overall effortless tracking."
)

val animations = listOf(R.raw.share_animation, R.raw.notifcation_animation, R.raw.timeline_animation)
@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun OnboardingScreen(
    navigator: DestinationsNavigator,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
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
            val isCurrentPage = pagerState.currentPage == currentPage
            
            val textOpacity = animateFloatAsState(
                targetValue = if (isCurrentPage) 1f else 0f,
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

                AnimatedVisibility(
                    visible = isCurrentPage,
                    enter = slideInVertically() + fadeIn(),
                    exit = fadeOut(),
                ) {
                    when (currentPage) {
                        0 -> CompanyLogoAnimation()
                        1 -> {
                            val permissionLauncher = rememberLauncherForActivityResult(
                                ActivityResultContracts.RequestPermission()
                            ) {}

                            LaunchedEffect(Unit) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                        }

                    }

                    LottieAnimation(
                        modifier = Modifier
                            .size(400.dp),
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        dynamicProperties = dynamicProperties
                    )
                }

                AnimatedVisibility(
                    visible = isCurrentPage,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 },
                    ),
                    exit = fadeOut()
                ) {
                    Text(
                        text = titles[currentPage],
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .alpha(textOpacity)
                    )
                }
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
        onComplete = {
            viewModel.completeOnboarding()
            navigator.navigate(HomeScreenDestination)

         },
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingButtons(
    pagerState: PagerState,
    onComplete: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PAGE_PADDING, vertical = 30.dp)
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
                onClick = onComplete
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
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary
            )
    )
}
