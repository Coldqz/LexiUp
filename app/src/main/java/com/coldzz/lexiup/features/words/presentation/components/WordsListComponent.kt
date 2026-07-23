package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

/**
 * Use [componentMode] to change this list mode. Also, if you want to add some last list element use [lastListElement], keep null if you don't need it.
 * @param actionOnElementClick is for clicking on the element, pass wordId inside
 * */
@Composable
fun WordsListComponent(
    wordsList: List<WordItemUiModel>,
    componentMode: WordListComponentMode,
    actionOnElementClick: (Int) -> Unit,
    descriptionUnderSearchBar: @Composable (() -> Unit)? = null,
    lastListElement: @Composable (() -> Unit)? = null,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        descriptionUnderSearchBar?.let {
            item {
                it.invoke()
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
                partOfSpeech = word.partOfSpeech.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                },
                actionOnElementClick = { actionOnElementClick(word.id) },
                componentMode = when (componentMode) {
                    is WordListComponentMode.StandardMode -> WordListElementMode.StandardMode()
                    is WordListComponentMode.MultiSelectMode -> WordListElementMode.MultiSelectMode(
                        checked = componentMode.checkedList.contains(word.id),
                        onSelectedChange = { componentMode.onSelectedChange(word.id) }
                    )

                    is WordListComponentMode.BookmarkMode -> WordListElementMode.BookmarkMode(
                        addedToReviewBlock = word.isInReviewBlock,
                        actionAddToReviewBlock = {
                            componentMode.actionAddToReviewBlock(word.id)
                        },
                        actionRemoveFromReviewBlock = {
                            componentMode.actionRemoveFromReviewBlock(
                                word.id
                            )
                        }
                    )
                }
            )
        }
        // here we can add single element if we need, keep null if you don't
        lastListElement?.let {
            item {
                lastListElement()
            }
        }
    }
}

sealed class WordListComponentMode {
    class StandardMode : WordListComponentMode()

    class MultiSelectMode(
        val checkedList: Set<Int>,
        val onSelectedChange: ((Int) -> Unit),
    ) : WordListComponentMode()

    class BookmarkMode(
        val actionAddToReviewBlock: (Int) -> Unit,
        val actionRemoveFromReviewBlock: (Int) -> Unit,
    ) : WordListComponentMode()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordsListComponentPreviewStandard() {
    LexiUpTheme {
        WordsListComponent(
            wordsList = FakeDataSamples.getUiModelMappedList(),
            componentMode = WordListComponentMode.StandardMode(),
            actionOnElementClick = { _ -> },
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordsListComponentPreviewMultiselect() {
    LexiUpTheme {
        WordsListComponent(
            wordsList = FakeDataSamples.getUiModelMappedList(),
            componentMode = WordListComponentMode.MultiSelectMode(
                checkedList = emptySet(),
                onSelectedChange = {}
            ),
            actionOnElementClick = { _ -> },
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordsListComponentPreviewBookmark() {
    LexiUpTheme {
        WordsListComponent(
            wordsList = FakeDataSamples.getUiModelMappedList(),
            componentMode = WordListComponentMode.BookmarkMode(
                actionAddToReviewBlock = {},
                actionRemoveFromReviewBlock = {}
            ),
            actionOnElementClick = { _ -> },
            modifier = Modifier
        )
    }
}