package com.coldzz.lexiup.features.blocks.domain

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

interface WordBlockRepository {
    fun getAllBlocks(): Flow<List<WordBlock>>

    suspend fun addWordsToBlock(blockId: Int, wordIdList: List<Int>)

    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    suspend fun getCachedReviewBlockId(): Int

    suspend fun addBlock(wordBlock: WordBlock)

    suspend fun addWordToReviewBlock(wordId: Int)

    suspend fun deleteWordFromReviewBlock(wordId: Int)

    suspend fun getWordsFromBlock(): Flow<List<WordsWithReviewBlockIndicator>>
}