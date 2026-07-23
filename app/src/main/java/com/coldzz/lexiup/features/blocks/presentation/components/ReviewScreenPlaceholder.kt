package com.coldzz.lexiup.features.blocks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.core.components.CircularWordsProgress
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun ReviewScreenPlaceholder(
    wordsNumber: Int,
    onStartButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val minWordsNumber = Constants.MIN_NUMBER_OF_WORDS_TO_START_REVIEW_BLOCK_QUIZ
    val isUnlocked = wordsNumber >= minWordsNumber
    val wordsLeftToUnlock = minWordsNumber - wordsNumber
    val percentForIndicator = (wordsNumber.toFloat() / (minWordsNumber.toFloat() / 100f)).coerceIn(0f, 100f)

    val title = if (isUnlocked) stringResource(R.string.ready_to_practice) else stringResource(R.string.unlock_vocabulary_quiz)
    val subtitle = if (isUnlocked)
        stringResource(R.string.ready_to_practice_description)
    else
        stringResource(R.string.add_more_words_to_begin, wordsLeftToUnlock)

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    CoreReviewScreenCardPlaceholder(
        iconElement = {
            CircularWordsProgress(
                title = if (wordsNumber > minWordsNumber) "$wordsNumber" else "${wordsNumber}/$minWordsNumber",
                subtitle = stringResource(R.string.words_label),
                percentageForIndicator = percentForIndicator,
                innerPadding = if (isLandscape) 12.dp else 34.dp
            )
        },
        title = title,
        subtitle = subtitle,
        isStartButtonEnabled = isUnlocked,
        onStartButtonClick = onStartButtonClick,
        isLandscape = isLandscape,
        modifier = modifier
    )
}

@Composable
private fun CoreReviewScreenCardPlaceholder(
    iconElement: @Composable () -> Unit,
    title: String,
    subtitle: String,
    isStartButtonEnabled: Boolean,
    onStartButtonClick: () -> Unit,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 8.dp else 16.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth()
        ) {
            if (isLandscape) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        iconElement()
                        Column {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Button(
                        onClick = onStartButtonClick,
                        enabled = isStartButtonEnabled,
                    ) {
                        StartButtonContent(isStartButtonEnabled)
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {
                        iconElement()
                        Column {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    Button(
                        onClick = onStartButtonClick,
                        enabled = isStartButtonEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StartButtonContent(isStartButtonEnabled)
                    }
                }
            }
        }
    }
}

@Composable
private fun StartButtonContent(isEnabled: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isEnabled) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = stringResource(R.string.lock_icon),
                modifier = Modifier.size(18.dp)
            )
        }
        Text(text = stringResource(R.string.start_quiz))
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
private fun ReviewBlockPlaceholderEnoughWordsLandscapePreview() {
    LexiUpTheme {
        Scaffold(
            bottomBar = {
                ReviewScreenPlaceholder(
                    wordsNumber = 25,
                    onStartButtonClick = {},
                )
            },
        ) { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Text area")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ReviewBlockPlaceholderNotEnoughWordsPreview() {
    LexiUpTheme {
        Scaffold(
            bottomBar = {
                ReviewScreenPlaceholder(
                    wordsNumber = 3,
                    onStartButtonClick = {}
                )
            },
        ) { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Text area")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ReviewBlockPlaceholderZeroWordsPreview() {
    LexiUpTheme {
        Scaffold(
            bottomBar = {
                ReviewScreenPlaceholder(
                    wordsNumber = 0,
                    onStartButtonClick = {}
                )
            },
        ) { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Text area")
            }
        }
    }
}