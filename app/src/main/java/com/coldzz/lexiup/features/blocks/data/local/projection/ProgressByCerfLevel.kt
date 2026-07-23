package com.coldzz.lexiup.features.blocks.data.local.projection

import androidx.room.ColumnInfo
import com.coldzz.lexiup.core.common.CerfLevel

data class ProgressByCerfLevel(
    val level: CerfLevel,
    @ColumnInfo("total_words")
    val totalWords: Int,
    @ColumnInfo("learned_words")
    val learnedWords: Int
)