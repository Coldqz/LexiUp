package com.coldzz.lexiup.features.blocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import java.time.LocalDateTime

@Entity(tableName = "word_block")
data class WordBlock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "learning_level") val learningLevel: LearningLevelIndicator = LearningLevelIndicator.One,
    @ColumnInfo(name = "block_type") val blockType: BlockTypes,
    @ColumnInfo(name = "available_at") val availableAt: LocalDateTime? = null,
    @ColumnInfo(name = "completed_at") val completedAt: LocalDateTime? = null,
    @ColumnInfo(name = "isPermanent") val isPermanent: Boolean = false
)