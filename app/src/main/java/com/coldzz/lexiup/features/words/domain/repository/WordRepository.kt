package com.coldzz.lexiup.features.words.domain.repository

import com.coldzz.lexiup.features.words.domain.model.OxfordWords

interface WordRepository {

    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    suspend fun addWord(word: OxfordWords)
}