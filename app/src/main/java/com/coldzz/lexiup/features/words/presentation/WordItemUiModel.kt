package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.core.common.CerfLevel

data class WordItemUiModel(
    val id: Int,
    val word: String,
    val partOfSpeech: String = "",
    val level: CerfLevel,
    val isLearned: Boolean = false,
    val isInReviewBlock: Boolean = false
)
