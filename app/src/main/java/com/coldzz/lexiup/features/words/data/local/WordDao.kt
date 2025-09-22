package com.coldzz.lexiup.features.words.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertAllWords(wordsList: List<OxfordWords>)

    @Query("SELECT * from oxford_words")
    fun getWords(): Flow<List<OxfordWords>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addWord(word: OxfordWords)
}