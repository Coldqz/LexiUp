package com.coldzz.lexiup.features.words.data.local.entities

import androidx.room.ColumnInfo

data class WordsWithReviewBlockIndicator(
    val id: Int,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val level: LevelCerf,
    @ColumnInfo(name = "is_learned")val isLearned: Boolean = false,
    val isInReviewBlock: Boolean
)