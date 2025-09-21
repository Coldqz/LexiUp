package com.coldzz.lexiup.features.words.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "gpt_answers",
    foreignKeys = [
        ForeignKey(
            entity = OxfordWords::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GptAnswers(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "word_id") val wordId: String,
    @ColumnInfo(name = "definitions_order") val definitionsOrder: String
)