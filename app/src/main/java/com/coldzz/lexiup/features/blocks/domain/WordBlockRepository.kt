package com.coldzz.lexiup.features.blocks.domain

import com.coldzz.lexiup.features.blocks.data.local.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.WordBlockWithOxfordWords
import kotlinx.coroutines.flow.Flow

interface WordBlockRepository {
    fun getAllBlocks(): Flow<List<WordBlock>>

    suspend fun addWordsToBlock(blockId: Int, wordIdList: List<Int>)

    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    suspend fun addBlock(wordBlock: WordBlock)
}