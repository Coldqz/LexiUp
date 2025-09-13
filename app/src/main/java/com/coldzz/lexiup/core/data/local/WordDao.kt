package com.coldzz.lexiup.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.coldzz.lexiup.features.words.data.local.OxfordWords

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWords(wordsList: List<OxfordWords>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: OxfordWords)
}