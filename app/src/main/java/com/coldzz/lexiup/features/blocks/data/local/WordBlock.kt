package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_block")
data class WordBlock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "block_type") val blockType: BlockType,
    @ColumnInfo(name = "is_learned") val isLearned: Boolean = false
)

enum class BlockType {
    REGULAR, CUSTOM
}