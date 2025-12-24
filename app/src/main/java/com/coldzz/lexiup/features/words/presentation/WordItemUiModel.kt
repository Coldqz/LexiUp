package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf

data class WordItemUiModel(
    val id: Int,
    val word: String,
    val partOfSpeech: String = "",
    val level: LevelCerf,
    val isLearned: Boolean = false,
    val isInReviewBlock: Boolean? = null
)
