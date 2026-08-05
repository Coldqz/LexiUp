package com.coldzz.lexiup.core.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.components.viewmodel.OnBoardingViewModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    OnBoardingScreenComponent(
        changeDataStoreData = {
            viewModel.setOnBoardedTrue()
        },
        onFinished = onFinished
    )
}

@Composable
private fun OnBoardingScreenComponent(
    changeDataStoreData: () -> Unit,
    onFinished: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val boardingData = listOf(
        OnBoardingPageData(
            title = stringResource(R.string.onboarding_title_1),
            description = stringResource(R.string.onboarding_desc_1),
            imageRes = R.drawable.ic_boarding_blocks
        ),
        OnBoardingPageData(
            title = stringResource(R.string.onboarding_title_3),
            description = stringResource(R.string.onboarding_desc_3),
            imageRes = R.drawable.ic_boarding_bookmark
        ),
        OnBoardingPageData(
            title = stringResource(R.string.onboarding_title_4),
            description = stringResource(R.string.onboarding_desc_4),
            imageRes = R.drawable.ic_boarding_calendar
        ),
        OnBoardingPageData(
            title = stringResource(R.string.onboarding_title_2),
            description = stringResource(R.string.onboarding_desc_2),
            imageRes = R.drawable.ic_boarding_progress
        )
    )

    val pagerState = rememberPagerState(pageCount = { boardingData.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        // Skip button at the top, navigate to the last page (wrapped in AnimatedVisibility for smooth entry/exit)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            this@Column.AnimatedVisibility(
                visible = pagerState.currentPage < boardingData.size - 1
            ) {
                TextButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(boardingData.size - 1)
                    }
                }) {
                    Text(text = stringResource(R.string.skip), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { index ->
            OnBoardingPage(
                page = boardingData[index],
                isLandscape = isLandscape
            )
        }

        // Bottom panel: Indicators and Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = if (isLandscape) 8.dp else 32.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page indicators (dots)
            Row(
                modifier = Modifier
                    .padding(bottom = if (isLandscape) 12.dp else 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                repeat(boardingData.size) { i ->
                    val isCurrentDotPicked = pagerState.currentPage == i

                    // Animated size and color for the current dot
                    val currentDotsWidth by animateDpAsState(
                        targetValue = if (isCurrentDotPicked) 30.dp else 10.dp,
                        label = "PageIndicatorWidth"
                    )
                    val animatedColor by animateColorAsState(
                        targetValue = if (isCurrentDotPicked)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        label = "IndicatorColor"
                    )
                    // Dots
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(animatedColor)
                            .size(
                                width = currentDotsWidth,
                                height = 10.dp
                            )
                    )
                }
            }

            // Navigation buttons
            Row(
                modifier = Modifier
                    .sizeIn(minHeight = 48.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back button (hidden on the first page)
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    this@Row.AnimatedVisibility(visible = pagerState.currentPage > 0) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.back))
                        }
                    }
                }

                // Next or Get Started button
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = {
                            if (pagerState.currentPage < boardingData.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                changeDataStoreData()
                                onFinished()
                            }
                        },
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = if (pagerState.currentPage == boardingData.size - 1)
                                stringResource(R.string.get_started) else stringResource(R.string.next),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: OnBoardingPageData,
    isLandscape: Boolean
) {
    val scrollState = rememberScrollState()
    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 40.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .heightIn(max = 200.dp)
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.description,
                    modifier = Modifier.widthIn(max = 488.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 40.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .heightIn(max = 280.dp)
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = page.description,
                modifier = Modifier.widthIn(max = 488.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Data class for onboarding page content
private data class OnBoardingPageData(
    val title: String,
    val description: String,
    val imageRes: Int
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnBoardingScreenPreview() {
    LexiUpTheme {
        OnBoardingScreenComponent(changeDataStoreData = {}, onFinished = {})
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
private fun OnBoardingScreenLandscapePreview() {
    LexiUpTheme {
        OnBoardingScreenComponent(changeDataStoreData = {}, onFinished = {})
    }
}
