package com.coldzz.lexiup.features.words.data.local.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldzz.lexiup.core.common.CerfLevel

@Immutable
@Entity(tableName = "oxford_words")
data class OxfordWords(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val level: CerfLevel,
    @ColumnInfo(name = "is_learned") val isLearned: Boolean = false
)

