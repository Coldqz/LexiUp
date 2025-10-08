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
    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    @Query("SELECT * from oxford_words")
    fun getWords(): Flow<List<OxfordWords>>

    // TODO: Delete this after as we never add new words
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addWord(word: OxfordWords)
}