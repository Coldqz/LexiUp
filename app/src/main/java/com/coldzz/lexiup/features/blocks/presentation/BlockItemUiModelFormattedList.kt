package com.coldzz.lexiup.features.blocks.presentation

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import java.time.LocalDateTime

data class BlockItemUiModel(
    val blockNumber: Int,
    val learningLevel: LearningLevelIndicator,
    val availableAt: LocalDateTime?,
    val completedAt: LocalDateTime?
)

class BlockItemUiModelFormattedList (
    val activeBlocks: MutableList<BlockItemUiModel> = mutableListOf(),
    val plannedBlocks: MutableList<BlockItemUiModel> = mutableListOf(),
    val learnedBlocks: MutableList<BlockItemUiModel> = mutableListOf()
) {
    companion object {
        private fun WordBlock.toUiModel(): BlockItemUiModel {
            return BlockItemUiModel(
                blockNumber = id,
                learningLevel = learningLevel,
                availableAt = availableAt,
                completedAt = completedAt
            )
        }

        fun formattedList(data: List<WordBlock>): BlockItemUiModelFormattedList {
            val formattedWordBlock = BlockItemUiModelFormattedList()
            data.forEach { wordBlock ->
                when {
                    wordBlock.blockType == BlockTypes.ACTIVE && !wordBlock.isPermanent -> formattedWordBlock.activeBlocks.add(wordBlock.toUiModel())
                    wordBlock.blockType == BlockTypes.PLANNED && !wordBlock.isPermanent -> formattedWordBlock.plannedBlocks.add(wordBlock.toUiModel())
                    wordBlock.blockType == BlockTypes.LEARNED && !wordBlock.isPermanent -> formattedWordBlock.learnedBlocks.add(wordBlock.toUiModel())
                }
            }
            return formattedWordBlock
        }
    }
}