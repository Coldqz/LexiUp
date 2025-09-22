package com.coldzz.lexiup.features.words.domain.repository

import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    suspend fun addWord(word: OxfordWords)

    fun getWords(): Flow<List<OxfordWords>>
}