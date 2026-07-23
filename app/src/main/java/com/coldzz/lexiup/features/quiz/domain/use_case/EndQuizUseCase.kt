package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.blocks.domain.use_case.IncreaseBlockLearnedTodayUseCase
import java.time.LocalDate
import javax.inject.Inject

/**
 * This useCase class is to update block data and save it in the database.
 * */
class EndQuizUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository,
    private val increaseBlockLearnedToday: IncreaseBlockLearnedTodayUseCase
) {
    suspend operator fun invoke(
        blockId: Int,
    ) {
        val currentLearningLevel = blockRepository.getBlockLearningLevel(blockId)

        // label to know if this block was just learned for the first time, if so then we update this in database
        val isNewBlock = currentLearningLevel.isFirstLearnStatus()

        // get next level
        val newLevel = currentLearningLevel.next()

        // get next date based on next level
        val nextDate = LocalDate.now().plusDays(newLevel.nextIntervalDays())

        /*Checks if block is completed. If next level is Three(last)
        then block is learned, and we change type to learned*/
        val isBlockLearned = newLevel.isLastLevel()

        if (isBlockLearned) {
            // if the block has a third level, it becomes the fourth and get learned status
            blockRepository.updateBlockProgress(
                blockId = blockId,
                // increase learning level by one
                learningLevelIndicator = newLevel,
                availableAt = null,
                // set completion time
                completedAt = LocalDate.now()
            )
            // change it type to learned
            blockRepository.makeBlockLearned(blockId)

            // increases the number of blocks learned today
            increaseBlockLearnedToday(isNewBlock)
        } else {
            // normal logic to increase level
            blockRepository.updateBlockProgress(
                blockId = blockId,
                // increase learning level by one
                learningLevelIndicator = newLevel,
                // increase availableAt date by enum ordinal as they match days we need. One have 1, Two have 2 and so on. First is 0 so we add 1.
                availableAt = nextDate,
                completedAt = null
            )

            // increases the number of blocks learned today
            increaseBlockLearnedToday(isNewBlock)
        }
    }
}
