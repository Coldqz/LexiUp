package com.coldzz.lexiup.features.words.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllWords(wordsList: List<OxfordWords>)

    @Query("SELECT * from oxford_words")
    fun getWords(): Flow<List<OxfordWords>>

    /*
    * This query is for retrieving all oxford words with isInReviewBlock indicator,
    * it also needs review block id to work, we can get it from blocks repository
    * */
    @Query("SELECT w.* , j.word_block_id," +
            "CASE WHEN j.word_block_id IS :reviewBlockId THEN 1 ELSE 0 END AS isInReviewBlock " +
            "FROM oxford_words AS w " +
            "LEFT JOIN word_block_oxford_words AS j " +
            "ON w.id = j.word_id ")
    fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>>

    // TODO: Delete this after as we never add new words
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addWord(word: OxfordWords)
}