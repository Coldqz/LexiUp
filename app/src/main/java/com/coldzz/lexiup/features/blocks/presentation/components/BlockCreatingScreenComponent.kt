package com.coldzz.lexiup.features.blocks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.components.WordListComponentMode
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun BlockCreatingScreenComponent(
    wordsList: List<WordItemUiModel>,
    searchBarList: List<WordItemUiModel>,
    isCreateButtonLoading: Boolean,
    listMode: WordListComponentMode,
    searchBarListMode: WordListComponentMode,
    actionOnElementClick: (Int) -> Unit,
    actionOnSuggestWordsButton: (() -> Unit),
    actionOnCreateButton: () -> Unit
) {
    Scaffold(
        bottomBar = {
            LoadingButtonComponent(
                text = stringResource(R.string.create_block),
                isLoadingEnabled = isCreateButtonLoading,
                actionOnCreateButton = actionOnCreateButton
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            WordListScreenComponent(
                wordsList = wordsList,
                searchBarList = searchBarList,
                searchBarListMode = searchBarListMode,
                actionOnElementClick = actionOnElementClick,
                listMode = listMode,
                descriptionUnderSearchBar = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = stringResource(R.string.block_creating_helper_text),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                },
                lastListElement = {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        onClick = actionOnSuggestWordsButton,
                    ) {
                        Text(
                            text = stringResource(R.string.suggest_more_words),
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BlockCreatingScreenComponentPreview() {
    LexiUpTheme {
        BlockCreatingScreenComponent(
            wordsList = FakeDataSamples.getUiModelMappedList(),
            searchBarList = emptyList(),
            isCreateButtonLoading = false,
            listMode = WordListComponentMode.StandardMode(),
            searchBarListMode = WordListComponentMode.StandardMode(),
            actionOnCreateButton = {},
            actionOnElementClick = {},
            actionOnSuggestWordsButton = {}
        )
    }
}