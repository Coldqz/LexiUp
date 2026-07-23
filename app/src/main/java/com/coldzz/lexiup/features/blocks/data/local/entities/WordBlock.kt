package com.coldzz.lexiup.features.blocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import java.time.LocalDate

@Entity(tableName = "word_block")
data class WordBlock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "learning_level") val learningLevel: LearningLevelIndicator = LearningLevelIndicator.Zero,
    @ColumnInfo(name = "block_type") val blockType: BlockTypes,
    // this field we have to operate with active blocks timer, it is to know when user can learn the block
    @ColumnInfo(name = "available_at") val availableAt: LocalDate? = null,
    // we show completedAt label in learned blocks
    @ColumnInfo(name = "completed_at") val completedAt: LocalDate? = null,
    @ColumnInfo(name = "isPermanent") val isPermanent: Boolean = false,
    // block number to numerate blocks
    @ColumnInfo(name = "block_number") val blockNumber: Int
)