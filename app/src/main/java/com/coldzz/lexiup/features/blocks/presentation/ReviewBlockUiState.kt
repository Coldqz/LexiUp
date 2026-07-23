package com.coldzz.lexiup.features.blocks.presentation

import com.coldzz.lexiup.features.words.presentation.WordItemUiModel

data class ReviewBlockUiState(
    val reviewWords: List<WordItemUiModel>,
    val allWords: List<WordItemUiModel>,
    val reviewBlockId: Int
)