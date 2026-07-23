package com.coldzz.lexiup.features.words.presentation

data class BlockWordsUiState(
    val blockNumber: Int = 0,
    val words: List<WordItemUiModel> = emptyList()
)