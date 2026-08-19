package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.core.common.CerfLevel

data class WordDetailsUiState(
    val id: Int = 0,
    val word: String = "",
    val phonetic: String = "",
    val audioUrl: String = "",
    val isAudioLoading: Boolean = false,
    val enablePlayButton: Boolean = false,
    val partOfSpeech: String = "",
    val level: CerfLevel = CerfLevel.Unknown,
    val definitionAndExamples: List<DefinitionAndExampleModel> = emptyList(),
    val isInReviewBlock: Boolean
)