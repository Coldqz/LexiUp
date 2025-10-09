package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldzz.lexiup.features.blocks.domain.BlockTypes

@Entity(tableName = "word_block")
data class WordBlock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "block_type") val blockType: BlockTypes,
    @ColumnInfo(name = "is_learned") val isLearned: Boolean = false
)