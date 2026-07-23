package com.coldzz.lexiup.features.words.data.local.projection

import androidx.room.ColumnInfo
import com.coldzz.lexiup.core.common.CerfLevel

data class WordsWithReviewBlockIndicator(
    val id: Int,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val level: CerfLevel,
    @ColumnInfo(name = "is_learned")val isLearned: Boolean = false,
    val isInReviewBlock: Boolean
)