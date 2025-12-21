package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.features.blocks.presentation.components.BlockCreatingScreenComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.BlockCreatingViewModel

private const val TAG = "BlockCreatingScreen"

@Composable
fun BlockCreatingScreen(blockCreatingViewModel: BlockCreatingViewModel = hiltViewModel()) {
    val blockCreatingUiState by blockCreatingViewModel.blockCreatingUiState.collectAsState()

    BlockCreatingScreenComponent(
        wordsList = blockCreatingUiState.wordsList,
        actionOnCreateButton = {},
        actionOnSuggestWordsButton = {},
        actionAddToReviewBlock = {},
        actionRemoveFromReviewBlock = {},
        checkedList = blockCreatingUiState.checkedList,
        onSelectedChange = { wordId ->
            blockCreatingViewModel.onSelectedChange(wordId)
        }
    )
}