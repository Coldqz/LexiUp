package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.components.WordListComponentMode
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun ReviewBlockScreenComponent(
    wordsList: List<WordItemUiModel>,
    searchBarList: List<WordItemUiModel>,
    actionOnElementClick: (Int) -> Unit,
    addWordToReviewBlock: (Int) -> Unit,
    removeWordFromReviewBlock: (Int) -> Unit,
    onStartButtonClick: () -> Unit,
) {
    val listMode = WordListComponentMode.BookmarkMode(
        actionAddToReviewBlock = { wordId ->
            addWordToReviewBlock(wordId)
        },
        actionRemoveFromReviewBlock = { wordId ->
            removeWordFromReviewBlock(wordId)
        }
    )
    WordListScreenComponent(
        wordsList = wordsList,
        searchBarListMode = listMode,
        searchBarList = searchBarList,
        emptyListComposable = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                ReviewScreenNoWordsCaseComponent()
            }
        },
        actionOnElementClick = actionOnElementClick,
        listMode = listMode,
        bottomBar = {
            ReviewScreenPlaceholder(
                wordsNumber = wordsList.size,
                onStartButtonClick = onStartButtonClick,
            )
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun ReviewBlockScreenComponentPreview() {
    LexiUpTheme {
        ReviewBlockScreenComponent(
            wordsList = emptyList(),
            searchBarList = emptyList(),
            actionOnElementClick = {},
            addWordToReviewBlock = {},
            removeWordFromReviewBlock = {},
            onStartButtonClick = {}
        )
    }
}