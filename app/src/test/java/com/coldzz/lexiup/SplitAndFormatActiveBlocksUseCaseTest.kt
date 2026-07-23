package com.coldzz.lexiup

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.blocks.domain.use_case.SplitActiveBlocksUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate

class SplitAndFormatActiveBlocksUseCaseTest {

    val useCase = SplitActiveBlocksUseCase()
    val isDailyLimitReached = false
    val isNewBlocksLimitReached = false

    private fun generateBlock(number: Int, level: LearningLevelIndicator): WordBlock {
        return WordBlock(
            id = number,
            blockType = BlockTypes.ACTIVE,
            learningLevel = level,
            availableAt = LocalDate.now(),
            blockNumber = number
        )
    }

    /*
    * Here we test if out split function do the work.
    * L is first learning block and R is repetition, we should have L+R R+L R+R but not L+L
    * */
    @Test
    fun `should return L and R when first is L`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.Zero)
            val block2 = generateBlock(2, LearningLevelIndicator.One)
            val block3 = generateBlock(3, LearningLevelIndicator.One)

            val blocks = listOf(
                block1, block2, block3
            )
            val result = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result.learnNowBlocks[0] == block1 && result.learnNowBlocks[1] == block2
            )
            assert(
                result.upcomingBlocks[0] == block3 && result.upcomingBlocks.size == 1
            )
        }
    }

    @Test
    fun `should return R and L when first is R`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.One)
            val block2 = generateBlock(2, LearningLevelIndicator.Zero)
            val block3 = generateBlock(3, LearningLevelIndicator.Zero)

            val blocks = listOf(
                block1, block2, block3
            )
            val result = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result.learnNowBlocks[0] == block1 && result.learnNowBlocks[1] == block2
            )
            assert(
                result.upcomingBlocks[0] == block3 && result.upcomingBlocks.size == 1
            )
        }
    }

    @Test
    fun `should return R and R when no L blocks exist`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.One)
            val block2 = generateBlock(2, LearningLevelIndicator.One)
            val block3 = generateBlock(3, LearningLevelIndicator.One)

            val blocks = listOf(
                block1, block2, block3
            )
            val result = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result.learnNowBlocks[0] == block1 && result.learnNowBlocks[1] == block2
            )
            assert(
                result.upcomingBlocks[0] == block3 && result.upcomingBlocks.size == 1
            )
        }
    }

    @Test
    fun `should return only one L item when list has only L blocks`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.Zero)
            val block2 = generateBlock(2, LearningLevelIndicator.Zero)
            val block3 = generateBlock(3, LearningLevelIndicator.Zero)

            val blocks = listOf(
                block1, block2, block3
            )
            val result = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result.learnNowBlocks[0] == block1
            )
            assert(
                result.upcomingBlocks[0] == block2 &&
                        result.upcomingBlocks[1] == block3 &&
                        result.upcomingBlocks.size == 2
            )
        }
    }

    @Test
    fun `should return only one R or L item when list has 1 item`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.Zero)
            val block2 = generateBlock(2, LearningLevelIndicator.One)

            //only L test
            val blocks = listOf(
                block1
            )

            // only R test
            val blocks2 = listOf(
                block2
            )

            val result1 = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result1.learnNowBlocks[0] == block1
            )
            assert(
                result1.upcomingBlocks.isEmpty()
            )

            val result2 = useCase(blocks2, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result2.learnNowBlocks[0] == block2
            )
            assert(
                result2.upcomingBlocks.isEmpty()
            )
        }
    }

    @Test
    fun `should return only upcomingBlocks when there are no blocks for today`() {
        runBlocking {
            val block1 = generateBlock(1, LearningLevelIndicator.Zero).copy(
                availableAt = LocalDate.now().plusDays(1)
            )
            val block2 = generateBlock(2, LearningLevelIndicator.Zero).copy(
                availableAt = LocalDate.now().plusDays(3)
            )
            val block3 = generateBlock(3, LearningLevelIndicator.Zero).copy(
                availableAt = LocalDate.now().plusDays(7)
            )

            val blocks = listOf(
                block1, block2, block3
            )
            val result = useCase(blocks, isDailyLimitReached, isNewBlocksLimitReached)
            assert(
                result.learnNowBlocks.isEmpty() && result.upcomingBlocks == blocks
            )
        }
    }
}