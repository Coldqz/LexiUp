package com.coldzz.lexiup.features.blocks.presentation

import com.coldzz.lexiup.features.blocks.domain.use_case.BlocksDayStatus

class BlocksScreenUiState (
    val dayStatus: BlocksDayStatus,
    val learnNowBlocks: List<BlockItemUiModel.Active> = emptyList(),
    val upcomingBlocks: List<BlockItemUiModel.Active> = emptyList(),
    val plannedBlocks: List<BlockItemUiModel.Planned> = emptyList(),
    val learnedBlocks: List<BlockItemUiModel.Learned> = emptyList(),
)  {
    val hasLearnNowBlocks: Boolean
        get() = learnNowBlocks.isNotEmpty()

    val hasUpcomingBlocks: Boolean
        get() = upcomingBlocks.isNotEmpty()

    val hasPlannedBlocks: Boolean
        get() = plannedBlocks.isNotEmpty()

    val hasLearnedBlocks: Boolean
        get() = learnedBlocks.isNotEmpty()

    val getActiveBlocksCount: Int
        get() = learnNowBlocks.size + upcomingBlocks.size
}