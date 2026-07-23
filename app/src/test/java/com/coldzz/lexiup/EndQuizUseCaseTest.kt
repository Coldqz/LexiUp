package com.coldzz.lexiup

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.projection.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.blocks.domain.use_case.IncreaseBlockLearnedTodayUseCase
import com.coldzz.lexiup.features.quiz.domain.use_case.EndQuizUseCase
import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import com.coldzz.lexiup.features.user.data.local.repository.UserRepository
import com.coldzz.lexiup.features.words.data.local.projection.BlockWordsListData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class EndQuizUseCaseTest {
    private val fakeBlockRepository = FakeBlockRepository()
    private val fakeUserRepository = FakeUserRepository()
    private val fakeIncreaseUseCase = IncreaseBlockLearnedTodayUseCase(fakeUserRepository)

    private val endUseCase = EndQuizUseCase(fakeBlockRepository, fakeIncreaseUseCase)
    private val id = 1

    @Before
    fun resetRepositoryData() {
        fakeBlockRepository.resetBlock(id)
    }

    @Test
    fun `should return day null and level Zero when endQuiz was not called`() {
        runBlocking {
            val block = fakeBlockRepository.getBlockWithId(id)
            assert(
                block.learningLevel == LearningLevelIndicator.Zero
            )
            assert(
                block.availableAt == null
            )
            assert(
                block.completedAt == null
            )
        }
    }

    @Test
    fun `should return day+1 and level One when endQuiz was called 1 time`() {
        runBlocking {
            endUseCase(id)
            val block = fakeBlockRepository.getBlockWithId(id)
            assert(
                block.learningLevel == LearningLevelIndicator.One
            )
            assert(
                block.availableAt == LocalDate.now().plusDays(1)
            )
        }
    }

    @Test
    fun `should return day+3 and level Two when endQuiz was called 2 times `() {
        runBlocking {
            endUseCase(id)
            endUseCase(id)
            val block = fakeBlockRepository.getBlockWithId(id)
            assert(
                block.learningLevel == LearningLevelIndicator.Two
            )
            assert(
                block.availableAt == LocalDate.now().plusDays(3)
            )
        }
    }

    @Test
    fun `should return day+7 and level Three when endQuiz was called 3 times`() {
        runBlocking {
            endUseCase(id)
            endUseCase(id)
            endUseCase(id)
            val block = fakeBlockRepository.getBlockWithId(id)
            assert(
                block.learningLevel == LearningLevelIndicator.Three
            )
            assert(
                block.availableAt == LocalDate.now().plusDays(7)
            )
        }
    }

    @Test
    fun `should return day null and level Four when endQuiz was called 4 times`() {
        runBlocking {
            endUseCase(id)
            endUseCase(id)
            endUseCase(id)
            endUseCase(id)
            val block = fakeBlockRepository.getBlockWithId(id)
            assert(
                block.learningLevel == LearningLevelIndicator.entries.last()
            )
            assert(
                block.availableAt == null
            )
            assert(
                block.completedAt == LocalDate.now()
            )
            assert(
                block.blockType == BlockTypes.LEARNED
            )
        }
    }
}

class FakeBlockRepository : WordBlockRepository {
    // activated block, not learned yet
    private var fakeBlock1 = WordBlock(
        id = 1,
        learningLevel = LearningLevelIndicator.Zero,
        blockType = BlockTypes.ACTIVE,
        blockNumber = 1,
        availableAt = null
    )

    /*private val fakeBlock2 = WordBlock(
        learningLevel = LearningLevelIndicator.Zero,
        blockType = BlockTypes.PLANNED,
        blockNumber = 1
    )
    private val fakeBlock3 = WordBlock(
        learningLevel = LearningLevelIndicator.Zero,
        blockType = BlockTypes.PLANNED,
        blockNumber = 1
    )
     */
    val blocks = mutableListOf(fakeBlock1)

    fun getBlockWithId(blockId: Int): WordBlock {
        val index = blocks.indexOfFirst { it.id == blockId }
        return blocks[index]
    }

    fun resetBlock(blockId: Int) {
        val index = blocks.indexOfFirst { it.id == blockId }
        blocks[index] = fakeBlock1
    }

    override fun getAllBlocks(): Flow<List<WordBlock>> {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun addWordsToBlock(
        blockId: Int,
        wordIdList: List<Int>
    ) {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>> {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun getCachedReviewBlockId(): Int {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun addBlock(wordBlock: WordBlock): Long {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun createBlockWithWords(
        wordBlock: WordBlock,
        wordIds: List<Int>
    ) {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun getMaxBlockNumber(): Int {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun addWordToReviewBlock(wordId: Int) {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun removeWordFromReviewBlock(wordId: Int) {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun getWordsFromReviewBlock(): Flow<BlockWordsListData> {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getWordsFromBlock(blockId: Int): Flow<BlockWordsListData> {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun deleteWordBlock(blockId: Int) {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun activateBlockIfPossible(blockId: Int): Boolean {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun deactivateBlock(blockId: Int) {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun updateBlockProgress(
        blockId: Int,
        learningLevelIndicator: LearningLevelIndicator,
        availableAt: LocalDate?,
        completedAt: LocalDate?
    ) {

        val index = blocks.indexOfFirst { it.id == blockId }
        if (index == -1) return

        val block = blocks[index]

        blocks[index] = block.copy(
            learningLevel = learningLevelIndicator,
            availableAt = availableAt,
            completedAt = completedAt
        )
    }

    override suspend fun getBlockLearningLevel(blockId: Int): LearningLevelIndicator {
        return blocks.first { it.id == blockId }.learningLevel
    }

    override suspend fun makeBlockLearned(blockId: Int) {
        val index = blocks.indexOfFirst { it.id == blockId }
        val block = blocks[index]

        blocks[index] = block.copy(
            blockType = BlockTypes.LEARNED
        )
    }

}

class FakeUserRepository : UserRepository {
    private var user = UserStats(
        id = 0,
        streak = 0,
        blocksLearnedToday = null,
        newBlocksLearnedToday = null,
        lastUpdateDate = null
    )

    override fun getUserFlow(): Flow<UserStats> {
        throw NotImplementedError("Not yet implemented")
    }

    override suspend fun getBlocksLearnedToday(): Int? {
        return user.blocksLearnedToday
    }

    override suspend fun increaseBlocksLearnedToday(newBlock: Boolean) {
        val learnedToday = user.blocksLearnedToday ?: 0
        user = user.copy(
            blocksLearnedToday = learnedToday + 1,
            lastUpdateDate = LocalDate.now()
        )
    }

    override suspend fun getLastUpdateDate(): LocalDate? {
        return user.lastUpdateDate
    }

}