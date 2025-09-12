package com.coldzz.lexiup.features.words.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_details",
    foreignKeys = [
        ForeignKey(
            entity = OxfordWords::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordDetails(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word_id") val wordId: Int,
    @ColumnInfo(name = "audio_us") val audioUs: String,
    @ColumnInfo(name = "audio_uk") val audioUk: String,
)