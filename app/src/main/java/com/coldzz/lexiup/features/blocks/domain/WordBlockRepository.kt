package com.coldzz.lexiup.features.blocks.domain

import androidx.room.Transaction
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.projection.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.words.data.local.projection.BlockWordsListData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WordBlockRepository {
    fun getAllBlocks(): Flow<List<WordBlock>>

    suspend fun addWordsToBlock(blockId: Int, wordIdList: List<Int>)

    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    suspend fun getCachedReviewBlockId(): Int

    suspend fun addBlock(wordBlock: WordBlock): Long

    suspend fun createBlockWithWords(wordBlock: WordBlock, wordIds: List<Int>)

    suspend fun getMaxBlockNumber(): Int

    suspend fun addWordToReviewBlock(wordId: Int)

    suspend fun removeWordFromReviewBlock(wordId: Int)

    suspend fun getWordsFromReviewBlock(): Flow<BlockWordsListData>

    fun getWordsFromBlock(blockId: Int): Flow<BlockWordsListData>

    suspend fun deleteWordBlock(blockId: Int)

    @Transaction
    suspend fun activateBlockIfPossible(blockId: Int): Boolean

    suspend fun deactivateBlock(blockId: Int)

    suspend fun updateBlockProgress(blockId: Int, learningLevelIndicator: LearningLevelIndicator, availableAt: LocalDate?, completedAt: LocalDate?)

    suspend fun getBlockLearningLevel(blockId: Int): LearningLevelIndicator

    suspend fun makeBlockLearned(blockId: Int)
}