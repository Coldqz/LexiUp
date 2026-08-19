package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.domain.ReviewBlockIndicator
import com.coldzz.lexiup.features.words.presentation.WordDetailsUiState
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDetailsComponent(
    modifier: Modifier = Modifier,
    uiState: WordDetailsUiState,
    actionOnPlayButton: (String) -> Unit,
    onBackButtonAction: () -> Unit,
    actionAddToReviewBlock: (Int) -> Unit,
    actionRemoveFromReviewBlock: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonAction,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_icon),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (uiState.isInReviewBlock) {
                                actionRemoveFromReviewBlock(uiState.id)
                            } else {
                                actionAddToReviewBlock(uiState.id)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = if (uiState.isInReviewBlock)
                                ImageVector.vectorResource(ReviewBlockIndicator.Remove.resourceId)
                            else
                                ImageVector.vectorResource(ReviewBlockIndicator.Add.resourceId),
                            contentDescription = if (uiState.isInReviewBlock)
                                stringResource(R.string.added_as_bookmark_icon)
                            else
                                stringResource(R.string.add_as_bookmark)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalIconButton(
                            onClick = { actionOnPlayButton(uiState.audioUrl) },
                            enabled = uiState.enablePlayButton
                        ) {
                            if (uiState.isAudioLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.ic_speaker),
                                    contentDescription = stringResource(R.string.play_american_pronunciation),
                                )
                            }
                        }
                        Text(
                            text = uiState.word,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        CerfLevelIconComponent(level = uiState.level)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.phonetic,
                            style = MaterialTheme.typography.labelMedium,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            text = " · ",
                            fontWeight = FontWeight.Bold
                        )
                        PartOfSpeechIconComponent(partOfSpeech = uiState.partOfSpeech)
                    }
                }
            }
            item {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_book),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(R.string.definitions_icon)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(R.string.definitions),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            itemsIndexed(uiState.definitionAndExamples) { index, item ->
                DefinitionCard(
                    number = index + 1,
                    definition = item.definition,
                    example = item.example
                )
            }
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun WordDetailComponentPreview() {
    LexiUpTheme {
        WordDetailsComponent(
            uiState = FakeDataSamples.fakeWordDefinitionSample,
            onBackButtonAction = {},
            actionRemoveFromReviewBlock = {},
            actionAddToReviewBlock = {},
            actionOnPlayButton = {}
        )
    }
}