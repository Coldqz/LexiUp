package com.coldzz.lexiup.features.blocks.data.local.repository

import androidx.room.Transaction
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.blocks.data.local.WordBlockDao
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.projection.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.projection.BlockWordsListData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class WordBlockRepositoryImpl @Inject constructor(private val dao: WordBlockDao) :
    WordBlockRepository {

    private var cachedReviewBlockId: Int? = null

    /**
    * Function to get our review block id and cache it after.
    * It queries the db, return id and caches id into cachedReviewBlockId
    * then every time just return the cached id until app repo is cleared.
    *
    * We call it every first time when we need reviewBlockId and thus id won't be null
    * */
    override suspend fun getCachedReviewBlockId(): Int {
        cachedReviewBlockId?.let {
            return it
        }
        val id = dao.getReviewBlockId()
        cachedReviewBlockId = id
        return id
    }

    override fun getAllBlocks(): Flow<List<WordBlock>> {
        return dao.getAllBlocks()
    }

    // function to assign multiple word ids to one block in our cross table WordBlockOxfordWords
    override suspend fun addWordsToBlock(blockId: Int, wordIdList: List<Int>) {
        val resultList = mutableListOf<WordBlockOxfordWords>()
        wordIdList.forEach { element ->
            resultList.add(
                WordBlockOxfordWords(
                    wordBlockId = blockId,
                    wordId = element
                )
            )
        }
        return dao.addWordsToBlock(
            wordBlockOxfordWordsList = resultList
        )
    }

    override suspend fun createBlockWithWords(wordBlock: WordBlock, wordIds: List<Int>) {
        return dao.createBlockWithWords(wordBlock, wordIds)
    }

    override fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>> {
        return dao.getWordBlockWithOxfordWords()
    }

    override suspend fun addBlock(wordBlock: WordBlock): Long {
        return dao.addBlock(wordBlock)
    }

    override suspend fun getMaxBlockNumber(): Int {
        return dao.getMaxBlockNumber()
    }

    override suspend fun addWordToReviewBlock(wordId: Int) {
        dao.addWordsToBlock(
            listOf(
                WordBlockOxfordWords(
                    wordBlockId = getCachedReviewBlockId(),
                    wordId = wordId
                )
            )
        )
    }

    override suspend fun removeWordFromReviewBlock(wordId: Int) {
        val reviewBlockId = getCachedReviewBlockId()
        dao.deleteWordFromBlock(
            listOf(
                WordBlockOxfordWords(
                    wordBlockId = reviewBlockId,
                    wordId = wordId
                )
            )
        )
    }

    override suspend fun getWordsFromReviewBlock(): Flow<BlockWordsListData> {
        return dao.getWordsFromBlock(blockId = getCachedReviewBlockId()).map { map ->
            map.entries.firstOrNull()?.let { entry ->
                BlockWordsListData(
                    block = entry.key,
                    words = entry.value
                )
            } ?: BlockWordsListData(
                block = WordBlock(blockType = BlockTypes.ACTIVE, blockNumber = 0),
                words = emptyList()
            )
        }
    }

    override fun getWordsFromBlock(blockId: Int): Flow<BlockWordsListData> {
        return dao.getWordsFromBlock(blockId = blockId).map { map ->
            val entry = map.entries.firstOrNull() ?: throw Exception("Block not found")
            BlockWordsListData(
                block = entry.key,
                words = entry.value
            )
        }
    }

    override suspend fun deleteWordBlock(blockId: Int) {
        dao.deleteBlock(blockId)
    }

    /**
     * Function checks if it is possible to add more active blocks if yes then add and return true,
     * if max count of active blocks was exceeded then do nothing and return false
     * */
    @Transaction
    override suspend fun activateBlockIfPossible(blockId: Int): Boolean {
        if (dao.getSpecificBlockTypeTotalNumber(blockType = BlockTypes.ACTIVE) >= Constants.MAX_ACTIVE_BLOCKS_COUNT) {
            return false
        } else {
            dao.updateBlockType(blockId = blockId, blockType = BlockTypes.ACTIVE)
            dao.updateBlockAvailability(blockId = blockId, availableAt = LocalDate.now())
            return true
        }
    }


    override suspend fun makeBlockLearned(blockId: Int) {
        dao.updateBlockType(
            blockId = blockId,
            blockType = BlockTypes.LEARNED
        )
    }

    override suspend fun deactivateBlock(blockId: Int) {
        dao.updateBlockType(
            blockId = blockId,
            blockType = BlockTypes.PLANNED
        )
        dao.updateLearningProgress(
            blockId = blockId,
            learningLevelIndicator = LearningLevelIndicator.Zero,
            availableAt = null,
            completedAt = null
        )
    }

    override suspend fun updateBlockProgress(
        blockId: Int,
        learningLevelIndicator: LearningLevelIndicator,
        availableAt: LocalDate?,
        completedAt: LocalDate?
    ) {
        dao.updateLearningProgress(
            blockId = blockId,
            learningLevelIndicator = learningLevelIndicator,
            availableAt = availableAt,
            completedAt = completedAt
        )

    }

    override suspend fun getBlockLearningLevel(blockId: Int): LearningLevelIndicator {
        return dao.getBlockLearningLevel(blockId)
    }
}