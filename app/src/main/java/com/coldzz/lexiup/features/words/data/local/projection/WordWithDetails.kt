package com.coldzz.lexiup.features.words.data.local.projection

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.coldzz.lexiup.core.common.CerfLevel
import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning

data class WordWithDetails(
    val id: Int,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String,
    val level: CerfLevel,
    @Relation(parentColumn = "id", entityColumn = "word_id") val wordDetails: WordDetails?,
    @Relation(parentColumn = "id", entityColumn = "word_id") val wordMeaning: List<WordMeaning>,
    val isInReviewBlock: Boolean
)