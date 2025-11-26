package com.coldzz.lexiup.features.blocks.data.local.entities

import androidx.room.ColumnInfo
import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf

data class WordPreviewDetail(
    val word: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val level: LevelCerf,
)