package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.core.common.Constants.DATE_FORMATTER
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.AvailabilityLabels
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.RawBlocksModel
import com.coldzz.lexiup.features.blocks.presentation.BlockItemUiModel
import com.coldzz.lexiup.features.blocks.presentation.BlocksScreenUiState
import javax.inject.Inject

/**
 * UseCase for building UI state
 * */
class MapRawBlocksUseCase @Inject constructor(
    private val generateLabel: GenerateAvailableAtLabelUseCase
) {
    operator fun invoke(rawBlocksModel: RawBlocksModel): BlocksScreenUiState {
        return BlocksScreenUiState(
            dayStatus = rawBlocksModel.dayStatus,
            learnNowBlocks = rawBlocksModel.learnNowBlocks.map {
                mapSingleBlock(
                    wordBlock = it,
                    isToLearnNow = true
                ) as BlockItemUiModel.Active
            },
            upcomingBlocks = rawBlocksModel.upcomingBlocks.map {
                mapSingleBlock(
                    wordBlock = it,
                    isToLearnNow = false
                ) as BlockItemUiModel.Active
            },
            plannedBlocks = rawBlocksModel.plannedBlocks.map {
                mapSingleBlock(
                    wordBlock = it,
                    isToLearnNow = false
                ) as BlockItemUiModel.Planned
            },
            learnedBlocks = rawBlocksModel.learnedBlocks.map {
                mapSingleBlock(
                    wordBlock = it,
                    isToLearnNow = false
                ) as BlockItemUiModel.Learned
            },
        )
    }

    /**
     * We use [isToLearnNow], to identify and turn on learning button on learnNowBlocks
     * */
    private fun mapSingleBlock(wordBlock: WordBlock, isToLearnNow: Boolean): BlockItemUiModel {
        return when (wordBlock.blockType) {
            BlockTypes.ACTIVE -> {
                val availableAtLabel = generateLabel(wordBlock.availableAt)
                BlockItemUiModel.Active(
                    id = wordBlock.id,
                    blockNumber = wordBlock.blockNumber,
                    learningLevel = wordBlock.learningLevel,
                    availableAt = availableAtLabel,
                    isLearnButtonActive = availableAtLabel == AvailabilityLabels.Today && isToLearnNow
                )
            }

            BlockTypes.PLANNED -> {
                BlockItemUiModel.Planned(
                    id = wordBlock.id,
                    blockNumber = wordBlock.blockNumber,
                )
            }

            BlockTypes.LEARNED -> {
                BlockItemUiModel.Learned(
                    id = wordBlock.id,
                    blockNumber = wordBlock.blockNumber,
                    completedAt = wordBlock.completedAt?.format(DATE_FORMATTER).orEmpty(),
                )
            }
        }
    }
}
