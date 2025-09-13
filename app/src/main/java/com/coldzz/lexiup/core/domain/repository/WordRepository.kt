package com.coldzz.lexiup.core.domain.repository

import com.coldzz.lexiup.features.words.data.local.OxfordWords

interface WordRepository {

    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    suspend fun addWord(word: OxfordWords)
}