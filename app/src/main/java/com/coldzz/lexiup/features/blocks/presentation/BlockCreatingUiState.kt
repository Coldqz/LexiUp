package com.coldzz.lexiup.features.blocks.presentation

import com.coldzz.lexiup.features.words.presentation.WordItemUiModel

data class BlockCreatingUiState(
    val wordsList: List<WordItemUiModel> = emptyList(),
    val searchBarList: List<WordItemUiModel> = emptyList(),
    val checkedList: Set<Int> = emptySet(),
    val isCreateButtonLoading: Boolean = false
)