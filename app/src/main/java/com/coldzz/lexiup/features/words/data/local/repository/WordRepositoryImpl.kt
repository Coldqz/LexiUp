package com.coldzz.lexiup.features.words.data.local.repository

import com.coldzz.lexiup.features.words.data.local.WordDao
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(private val dao: WordDao): WordRepository {
    override suspend fun insertAllWords(wordsList: List<OxfordWords>) {
        return dao.insertAllWords(wordsList)
    }

    override fun getWords(): Flow<List<OxfordWords>> {
        return dao.getWords()
    }

    override suspend fun addWord(word: OxfordWords) {
        return dao.addWord(word)
    }

}