package com.coldzz.lexiup.features.words.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_definitions",
    foreignKeys = [
        ForeignKey(
            entity = WordDetails::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordDefinitions(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "word_id") val wordId: Int,
    val definition: String
)