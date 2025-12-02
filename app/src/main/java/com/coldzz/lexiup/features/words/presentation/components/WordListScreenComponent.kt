package com.coldzz.lexiup.features.words.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.ui.theme.LexiUpTheme

private const val TAG = "WordListScreenComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordListScreenComponent(
    wordsList: List<WordsWithReviewBlockIndicator>,
    enableSearchBar: Boolean,
    actionAddToReviewBlock: (wordId: Int) -> Unit,
    actionRemoveFromReviewBlock: (wordId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                if (enableSearchBar) {
                    MySearchBar(
                        dataForSearch = wordsList,
                        actionAddToReviewBlock = actionAddToReviewBlock,
                        actionRemoveFromReviewBlock = actionRemoveFromReviewBlock
                    )
                }

            }

        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {
            items(
                wordsList,
                key = { word ->
                    word.id
                }
            ) { word ->
                WordListElement(
                    title = word.word,
                    level = word.level,
                    partOfSpeech = word.partOfSpeech,
                    // TODO: hardcoded for testing
                    isAddedToReviewBlock = word.isInReviewBlock,
                    actionAddToReviewBlock = { actionAddToReviewBlock(word.id) },
                    actionRemoveFromReviewBlock = { actionRemoveFromReviewBlock(word.id) }
                )
            }
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordListScreenPreview() {

    // TODO: fix this
    LexiUpTheme {
//        WordListScreenContentComponent(FakeDataSamples.fakeWordsList1)
    }
}