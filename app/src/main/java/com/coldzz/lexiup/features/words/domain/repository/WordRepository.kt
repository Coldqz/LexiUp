package com.coldzz.lexiup.features.words.domain.repository

import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import com.coldzz.lexiup.features.words.data.local.projection.WordWithDetails
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun insertWords(wordsList: List<OxfordWords>)

    suspend fun getWords(wordIdList: List<Int>): List<OxfordWords>

    suspend fun getWordsCount(): Flow<Int>

    fun getAllWordsFlow(): Flow<List<OxfordWords>>

    fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>>

    suspend fun getSingleWordDetailsFlow(
        wordId: Int,
        reviewBlockId: Int
    ): Flow<ResultDataState<WordWithDetails>>

    fun getPickQuizWordsFlow(blockId: Int): Flow<ResultDataState<List<PickQuizWordsData>>>

    suspend fun getRandomWords(limit: Int, reviewBlockId: Int, avoidIds: List<Int>): List<OxfordWords>
}