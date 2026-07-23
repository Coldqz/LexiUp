package com.coldzz.lexiup.features.words.data.local.projection

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning

data class PickQuizWordsData(
    val id: Int,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String,
    @Relation(
        parentColumn = "id",
        entityColumn = "word_id"
    )
    val definition: List<WordMeaning>
)