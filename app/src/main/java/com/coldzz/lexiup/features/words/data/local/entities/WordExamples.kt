package com.coldzz.lexiup.features.words.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_examples",
    foreignKeys = [
        ForeignKey(
            entity = WordDetails::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordExamples(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word_id") val wordId: Int,
    val example: String
)