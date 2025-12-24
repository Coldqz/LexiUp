package com.coldzz.lexiup.features.words.presentation.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.coldzz.lexiup.ui.theme.LexiUpTheme

private const val TAG = "WordListComponent"

/**
 * We use [checkedList] to introduce multiselection logic here, keep it null if u don't need it.
 *
 * */
@Composable
fun WordsListComponent(
    wordsList: List<WordItemUiModel>,
    actionAddToReviewBlock: (Int) -> Unit,
    actionRemoveFromReviewBlock: (Int) -> Unit,
    labelUnderSearchButton: String? = null,
    actionOnSuggestWordsButton: (() -> Unit)? = null,
    checkedList: Set<Int>? = null,
    onSelectedChange: ((Int) -> Unit)? = null,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        labelUnderSearchButton?.let {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = labelUnderSearchButton,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
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
                actionRemoveFromReviewBlock = { actionRemoveFromReviewBlock(word.id) },
                isSelected = checkedList?.contains(word.id),
                onSelectedChange = if (onSelectedChange != null) {
                    {
                        Log.d(TAG, "onSelectedChange was invoked word id is: ${word.id}")
                        onSelectedChange(word.id)
                    }
                } else null,
            )
        }
        actionOnSuggestWordsButton?.let {
            item {
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
        }
    }
}


@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordsListComponentPreview() {
    LexiUpTheme {
        WordsListComponent(
            wordsList = FakeDataSamples.getUiModelMappedList(),
            actionAddToReviewBlock = {},
            actionRemoveFromReviewBlock = {},
            labelUnderSearchButton = stringResource(R.string.block_creating_helper_text),
            actionOnSuggestWordsButton = {},
            modifier = Modifier
        )
    }
}