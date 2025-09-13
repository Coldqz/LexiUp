package com.coldzz.lexiup.features.words.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oxford_words")
data class OxfordWords(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val level: LevelCerf,
    @ColumnInfo(name = "is_learned") val isLearned: Boolean = false
)

enum class LevelCerf {
    A1, A2, B1, B2, C1, C2
}