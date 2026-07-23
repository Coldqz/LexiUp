package com.coldzz.lexiup.features.quiz.presentation

import com.coldzz.lexiup.features.quiz.presentation.components.AnswerOptionUiModel

data class QuizUiState(
    val choices: List<AnswerOptionUiModel> = emptyList(),
    val definition: String = "",
    val partOfSpeech: String = "",
    val currentProgressValue: Int = 0,
    val maxProgressValue: Int = 0,
    val currentWordId: Int? = null,
    val showCongratulationDialog: Boolean = false,
    val skippedWords: List<String> = emptyList()
)