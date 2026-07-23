package com.coldzz.lexiup.features.blocks.domain

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.use_case.BlocksDayStatus

data class RawBlocksModel(
    val dayStatus: BlocksDayStatus,
    val learnNowBlocks: List<WordBlock> = emptyList(),
    val upcomingBlocks: List<WordBlock> = emptyList(),
    val plannedBlocks: List<WordBlock> = emptyList(),
    val learnedBlocks: List<WordBlock> = emptyList()
)