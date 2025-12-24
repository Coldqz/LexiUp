package com.coldzz.lexiup.features.words.data.local.repository

import com.coldzz.lexiup.features.words.data.local.WordDao
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(private val dao: WordDao): WordRepository {

    private var cachedWordsCount: Int? = null

    override suspend fun getCachedWordsCount(): Int {
        cachedWordsCount?.let {
            return it
        }
        val id = dao.getWordsCount()
        cachedWordsCount = id
        return id
    }

    override suspend fun insertWords(wordsList: List<OxfordWords>) {
        return dao.insertWords(wordsList)
    }

    override suspend fun getWords(wordIdList: List<Int>): List<OxfordWords> {
        return dao.getWords(wordIdList)
    }

    override fun getAllWordsFlow(): Flow<List<OxfordWords>> {
        return dao.getAllWordsFlow()
    }

    override suspend fun addWord(word: OxfordWords) {
        return dao.addWord(word)
    }

    override fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>> {
        return dao.getWordsAndReviewBlockIndicator(reviewBlockId = reviewBlockId)
    }
}