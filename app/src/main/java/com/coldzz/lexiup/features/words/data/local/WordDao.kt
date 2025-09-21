package com.coldzz.lexiup.features.words.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.coldzz.lexiup.features.words.domain.model.OxfordWords

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertAllWords(wordsList: List<OxfordWords>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addWord(word: OxfordWords)
}