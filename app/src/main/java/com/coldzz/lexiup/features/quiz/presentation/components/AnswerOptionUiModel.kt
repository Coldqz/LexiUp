package com.coldzz.lexiup.features.quiz.presentation.components

data class AnswerOptionUiModel(
    val wordId: Int,
    val word: String,
    val partOfSpeech: String? = null
)