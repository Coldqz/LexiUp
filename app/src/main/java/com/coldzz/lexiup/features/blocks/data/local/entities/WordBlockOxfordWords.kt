package com.coldzz.lexiup.features.blocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords

@Entity(
    tableName = "word_block_oxford_words",
    primaryKeys = ["word_block_id", "word_id"],
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
    @ColumnInfo(name = "word_block_id")val wordBlockId:Int,
    @ColumnInfo(name = "word_id") val wordId: Int
)