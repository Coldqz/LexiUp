package com.coldzz.lexiup.features.blocks.data.local.repository

import com.coldzz.lexiup.features.blocks.data.local.WordBlockDao
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "WordBlockRepositoryImpl"

class WordBlockRepositoryImpl @Inject constructor(private val dao: WordBlockDao) :
    WordBlockRepository {

    private var cachedReviewBlockId: Int? = null

    /*
    * Function to get our review block id and cache it after.
    * It query the db, return id and caches id into cachedReviewBlockId
    * then every time just return the cached id until app repo is cleared
    * */
    private suspend fun getCachedReviewBlockId(): Int {
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

    override fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>> {
        return dao.getWordBlockWithOxfordWords()
    }

    override suspend fun addBlock(wordBlock: WordBlock) {
        dao.addBlock(wordBlock)
    }

    override suspend fun addWordToReviewBlock(wordId: Int) {
        val reviewBlockId = getCachedReviewBlockId()
        dao.addWordsToBlock(
            listOf(
                WordBlockOxfordWords(
                    wordBlockId = reviewBlockId,
                    wordId = wordId
                )
            )
        )
    }

    override suspend fun deleteWordFromReviewBlock(wordId: Int) {
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

    override suspend fun getReviewBlockWords(): Flow<List<OxfordWords>> {
        return dao.getReviewBlockWords()
    }
}