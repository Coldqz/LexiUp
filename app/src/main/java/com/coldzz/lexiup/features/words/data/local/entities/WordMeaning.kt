package com.coldzz.lexiup.features.words.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_meaning",
    foreignKeys = [
        ForeignKey(
            entity = WordDetails::class,
            parentColumns = arrayOf("word_id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordMeaning(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "word_id") val wordId: Int,
    val definition: String,
    val example: String?
)