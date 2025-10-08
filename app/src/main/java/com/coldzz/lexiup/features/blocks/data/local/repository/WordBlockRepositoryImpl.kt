package com.coldzz.lexiup.features.blocks.data.local.repository

import com.coldzz.lexiup.features.blocks.data.local.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.WordBlockDao
import com.coldzz.lexiup.features.blocks.data.local.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WordBlockRepositoryImpl @Inject constructor(private val dao: WordBlockDao) :
    WordBlockRepository {
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

    override fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>> {
        return dao.getWordBlockWithOxfordWords()
    }

    override suspend fun addBlock(wordBlock: WordBlock) {
        dao.addBlock(wordBlock)
    }

}