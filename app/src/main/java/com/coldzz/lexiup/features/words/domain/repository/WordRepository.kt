package com.coldzz.lexiup.features.words.domain.repository

import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    suspend fun addWord(word: OxfordWords)

    fun getWords(): Flow<List<OxfordWords>>

    fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>>
}