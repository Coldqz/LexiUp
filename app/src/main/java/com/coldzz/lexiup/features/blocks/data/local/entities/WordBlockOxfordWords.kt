package com.coldzz.lexiup.features.blocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords

@Entity(
    tableName = "word_block_oxford_words",
    foreignKeys = [
        ForeignKey(
            entity = OxfordWords::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordBlock::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_block_id"),
            onDelete = ForeignKey.CASCADE
        )

    ]
)
data class WordBlockOxfordWords(
    // TODO: check if we need id in cross table
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "word_block_id")val wordBlockId:Int,
    @ColumnInfo(name = "word_id") val wordId: Int
)