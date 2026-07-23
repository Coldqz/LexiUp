package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.RawBlocksModel
import com.coldzz.lexiup.features.blocks.presentation.BlocksScreenUiState
import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import javax.inject.Inject

class BuildBlocksUiStateUseCase @Inject constructor(
    private val mapRawBlocks: MapRawBlocksUseCase,
    private val calculateIfLimitReached: CalculateIfDailyLimitReachedUseCase,
    private val calculateIfDailyBlocksLimitReached: CalculateIfNewBlocksDailyLimitReachedUseCase,
    private val splitAndFormatActiveBlocks: SplitActiveBlocksUseCase
) {
    /**
     * Does all the work to build blocks uiState
     * */
    operator fun invoke(blocksData: List<WordBlock>, userStats: UserStats): BlocksScreenUiState {

        val plannedBlocks = mutableListOf<WordBlock>()
        val learnedBlocks = mutableListOf<WordBlock>()

        // calculate if day learning limit was reached
        val isLimitReached = calculateIfLimitReached(
            blocksLearnedToday = userStats.blocksLearnedToday,
            lastUpdateDate = userStats.lastUpdateDate
        )

        val isNewBlocksLimitReached = calculateIfDailyBlocksLimitReached(
            newBlocksLearnedToday = userStats.newBlocksLearnedToday,
            lastUpdateDate = userStats.lastUpdateDate
        )

        // first we filter out reviewBlock
        val listWithoutReviewBlock = blocksData.filterNot { it.isPermanent }

        /* Then we filter only activeBlocks so that we can
        calculate how to split them into learning and upcoming categories */
        val activeBlocks = listWithoutReviewBlock.filter { it.blockType == BlockTypes.ACTIVE }
        val rawBlocksDivided = splitAndFormatActiveBlocks(activeBlocks, isLimitReached, isNewBlocksLimitReached)

        // filter and insert planned and learned categories
        blocksData.forEach { wordBlock ->
            when (wordBlock.blockType) {
                BlockTypes.PLANNED -> plannedBlocks.add(wordBlock)
                BlockTypes.LEARNED -> learnedBlocks.add(wordBlock)
                BlockTypes.ACTIVE -> {}
            }
        }

        return mapRawBlocks(
            RawBlocksModel(
                dayStatus = rawBlocksDivided.dayStatus,
                learnNowBlocks = rawBlocksDivided.learnNowBlocks,
                upcomingBlocks = rawBlocksDivided.upcomingBlocks,
                plannedBlocks = plannedBlocks,
                learnedBlocks = learnedBlocks
            )
        )
    }
}