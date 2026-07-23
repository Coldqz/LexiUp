package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.CreateBlockResult
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import javax.inject.Inject

class CreateBlockWithWordsUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository,
) {
    suspend operator fun invoke(wordIds: List<Int>): CreateBlockResult {
        return if (wordIds.size < Constants.MIN_WORDS_IN_BLOCK) {
            CreateBlockResult.MinWordsNotReached
        } else {
            val wordBlockToAdd = WordBlock(
                learningLevel = LearningLevelIndicator.Zero,
                blockType = BlockTypes.PLANNED,
                blockNumber = blockRepository.getMaxBlockNumber() + 1
            )
            blockRepository.createBlockWithWords(
                wordBlock = wordBlockToAdd,
                wordIds = wordIds
            )
            CreateBlockResult.Success
        }
    }
}