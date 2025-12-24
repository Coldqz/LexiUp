package com.coldzz.lexiup.features.words.domain.repository

import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun insertWords(wordsList: List<OxfordWords>)

    suspend fun addWord(word: OxfordWords)

    suspend fun getWords(wordIdList: List<Int>): List<OxfordWords>

    suspend fun getCachedWordsCount(): Int

    fun getAllWordsFlow(): Flow<List<OxfordWords>>

    fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>>
}