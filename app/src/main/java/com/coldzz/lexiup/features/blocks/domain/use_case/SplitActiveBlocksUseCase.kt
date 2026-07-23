package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.RawBlocksModel
import java.time.LocalDate
import javax.inject.Inject

class SplitActiveBlocksUseCase @Inject constructor(
) {
    /**
     * Parameter [isDailyLimitReached] serves to identify whether day learning limit was reached.
     * If yes then we hide blocks for today even if they can be learned.
     *
     * Parameter [isNewBlocksLimitReached] manage new blocks limit.
     * If true then user already learned one new block, and we will filter other new ones for the next day. Only 1 new block per day is possible.
     * */
    operator fun invoke(
        rawActiveBlocks: List<WordBlock>,
        isDailyLimitReached: Boolean,
        isNewBlocksLimitReached: Boolean
    ): RawBlocksModel {

        val blocks = rawActiveBlocks.sortedBy { it.blockNumber }
        val allLearnTodayBlocks = blocks.filter {
            it.availableAt == null || !it.availableAt.isAfter(LocalDate.now())
        }

        val todayRepetitionBlocks =
            allLearnTodayBlocks.filter { !it.learningLevel.isFirstLearnStatus() }

        // boolean to check if we have space to activate more blocks
        val newBlockCanBeAdded = blocks.size < Constants.MAX_ACTIVE_BLOCKS_COUNT

        // boolean to check if user can repeat any block
        val canRepeatBlocks = todayRepetitionBlocks.isNotEmpty()

        return when {
            isDailyLimitReached -> {
                // case if day learning limit is reached
                RawBlocksModel(
                    dayStatus = BlocksDayStatus.DailyLimitReached,
                    learnNowBlocks = mutableListOf(),
                    upcomingBlocks = blocks
                )
            }

            isNewBlocksLimitReached -> {
                if (canRepeatBlocks) {
                    RawBlocksModel(
                        dayStatus = BlocksDayStatus.CanLearn,
                        learnNowBlocks = todayRepetitionBlocks,
                        upcomingBlocks = blocks - todayRepetitionBlocks
                    )
                } else {
                    RawBlocksModel(
                        dayStatus = BlocksDayStatus.NewBlockDailyLimitReached,
                        learnNowBlocks = todayRepetitionBlocks,
                        upcomingBlocks = blocks
                    )
                }
            }


            allLearnTodayBlocks.isEmpty() -> {
                // case if there are no blocks for today
                if (newBlockCanBeAdded) {
                    RawBlocksModel(
                        dayStatus = BlocksDayStatus.CanLearn,
                        learnNowBlocks = mutableListOf(),
                        upcomingBlocks = blocks
                    )
                } else {
                    RawBlocksModel(
                        dayStatus = BlocksDayStatus.Rest,
                        learnNowBlocks = mutableListOf(),
                        upcomingBlocks = blocks
                    )
                }
            }

            allLearnTodayBlocks.size == 1 && !isNewBlocksLimitReached && !isDailyLimitReached -> {

                // Case if only 1 block is available for today and limits aren't hit.
                // We exclude it from learn now list and add to upcoming
                RawBlocksModel(
                    dayStatus = BlocksDayStatus.CanLearn,
                    learnNowBlocks = allLearnTodayBlocks,
                    upcomingBlocks = blocks - allLearnTodayBlocks
                )
            }


            else -> {
                // main logic for list split
                val learningNowList = buildList {
                    val first = allLearnTodayBlocks.first()
                    // here we add first element as they are sorted
                    add(first)

                    /*
                    Here we add first element matching out logic. For example L is first learn block and R is repetition.
                    We can have only L+R, R+R, R+L. L+L if forbidden as they are both new.
                    */
                    val secondItemNew = if (first.learningLevel.isFirstLearnStatus()) {
                        allLearnTodayBlocks.firstOrNull { !it.learningLevel.isFirstLearnStatus() }
                    } else {
                        val findLearningItem =
                            allLearnTodayBlocks.firstOrNull { it.learningLevel.isFirstLearnStatus() }
                        findLearningItem
                            ?: allLearnTodayBlocks.firstOrNull { !it.learningLevel.isFirstLearnStatus() && it != first }
                    }
                    secondItemNew?.let { add(it) }
                }

                RawBlocksModel(
                    dayStatus = BlocksDayStatus.CanLearn,
                    learnNowBlocks = learningNowList.toMutableList(),
                    upcomingBlocks = blocks - learningNowList,
                )
            }
        }
    }
}