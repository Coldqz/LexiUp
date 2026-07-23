package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.components.WordListComponentMode
import com.coldzz.lexiup.features.words.presentation.components.WordsListComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockWordsListComponent(
    blockNumber: Int,
    wordsList: List<WordItemUiModel>,
    onWordClickAction: (Int) -> Unit,
    onBackButtonAction: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.word_block, blockNumber),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonAction,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_icon),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        WordsListComponent(
            wordsList = wordsList,
            componentMode = WordListComponentMode.StandardMode(),
            actionOnElementClick = { wordId ->
                onWordClickAction(wordId)
            },
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowBlockWordsComponentPreview() {
    BlockWordsListComponent(
        blockNumber = 2,
        wordsList = FakeDataSamples.getUiModelMappedList(),
        onWordClickAction = {},
        onBackButtonAction = {}
    )
}