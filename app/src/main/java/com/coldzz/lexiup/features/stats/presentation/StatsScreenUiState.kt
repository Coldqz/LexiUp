package com.coldzz.lexiup.features.stats.presentation

data class StatsScreenUiState(
    val currentlyLearnedWords: Int = 0,
    val remainingWords: Int = 0,
    val totalWordsNumber: Int = 0,
    val learnedPercentage: Float = 0f,
    val levelProgressDataModel: List<LevelProgressDataModel> = emptyList()
)