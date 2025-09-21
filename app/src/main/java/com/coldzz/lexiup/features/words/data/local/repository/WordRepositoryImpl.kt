package com.coldzz.lexiup.features.words.data.local.repository

import com.coldzz.lexiup.core.data.local.WordDao
import com.coldzz.lexiup.core.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.data.local.OxfordWords
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(private val dao: WordDao): WordRepository {
    override suspend fun insertAllWords(wordsList: List<OxfordWords>) {
        return dao.insertAllWords(wordsList)
    }

    override suspend fun addWord(word: OxfordWords) {
        return dao.addWord(word)
    }

}