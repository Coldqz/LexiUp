package com.coldzz.lexiup.features.blocks.presentation.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent
import com.coldzz.lexiup.ui.theme.LexiUpTheme

private const val TAG = "BlockCreatingScreenComponent"

@Composable
fun BlockCreatingScreenComponent(
    wordsList: List<WordsWithReviewBlockIndicator>,
    actionOnCreateButton: () -> Unit,
    actionOnSuggestWordsButton: () -> Unit,
    actionAddToReviewBlock: (Int) -> Unit,
    actionRemoveFromReviewBlock: (Int) -> Unit,
    checkedList: Set<Int>,
    onSelectedChange: ((Int) -> Unit)
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = actionOnSuggestWordsButton,
            ) {
                Text(
                    text = stringResource(R.string.create_block),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            WordListScreenComponent(
                wordsList = wordsList,
                enableSearchBar = true,
                actionAddToReviewBlock = { },
                actionRemoveFromReviewBlock = {},
                actionOnSuggestWordsButton = actionOnSuggestWordsButton,
                labelUnderSearchButton = stringResource(R.string.block_creating_helper_text),
                checkedList = checkedList,
                onSelectedChange = onSelectedChange
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
    val checkedList = remember { mutableStateSetOf(5221, 3815, 5882) }
    LexiUpTheme {
        BlockCreatingScreenComponent(
            wordsList = FakeDataSamples.getMappedList(),
            actionOnCreateButton = {},
            actionOnSuggestWordsButton = {},
            actionAddToReviewBlock = {},
            actionRemoveFromReviewBlock = {},
            checkedList = checkedList,
            onSelectedChange = { wordId ->
                Log.d(TAG, "onSelectedChange was invoked, wordId is: $wordId")
                if (!checkedList.contains(wordId)) {
                    checkedList.add(wordId)
                } else checkedList.remove(wordId)
            }
        )
    }
}