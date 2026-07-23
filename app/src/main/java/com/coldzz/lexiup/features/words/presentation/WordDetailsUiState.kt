package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.core.common.CerfLevel

data class WordDetailsUiState(
    val id: Int = 0,
    val word: String = "",
    val phonetic: String = "",
    val audioUs: String = "",
    val audioUk: String = "",
    val isUsAudioLoading: Boolean = false,
    val isUkAudioLoading: Boolean = false,
    val enableAmericanButton: Boolean = false,
    val enableBritishButton: Boolean = false,
    val partOfSpeech: String = "",
    val level: CerfLevel = CerfLevel.Unknown,
    val definitionAndExamples: List<DefinitionAndExampleModel> = emptyList(),
    val isInReviewBlock: Boolean
)