package com.coldzz.lexiup.features.words.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import com.coldzz.lexiup.features.words.data.local.projection.WordWithDetails
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(wordsList: List<OxfordWords>)

    @Query("SELECT * FROM oxford_words WHERE id IN (:wordIdList)")
    suspend fun getWords(wordIdList: List<Int>): List<OxfordWords>

    @Query("SELECT * from oxford_words")
    fun getAllWordsFlow(): Flow<List<OxfordWords>>

    @Query("SELECT max(id) FROM oxford_words")
    fun getWordsCount():Flow<Int>

    /**
    * This query is for retrieving all 5k+ oxford words with isInReviewBlock indicator,
    * it also needs review block id to work, we can get it from blocks repository
    * */
    @Query("SELECT w.*," +
            "CASE WHEN EXISTS (SELECT * FROM word_block_oxford_words AS j WHERE j.word_id = w.id AND j.word_block_id = :reviewBlockId) THEN 1 ELSE 0 END AS isInReviewBlock " +
            "FROM oxford_words AS w ")
    fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>>

    /**
     * Query get full word details for detail screen including also if word is in reviewBlock,
     * it also needs review block id to work, we can get it from blocks repository
     * */
    @Transaction
    @Query("SELECT w.id, w.word, w.part_of_speech, w.level," +
            "CASE WHEN EXISTS " +
            "(SELECT 1 FROM word_block_oxford_words AS j WHERE j.word_id =  w.id AND j.word_block_id = :reviewBlockId) THEN 1 ELSE 0 END AS isInReviewBlock " +
            "FROM oxford_words as w " +
            "WHERE w.id = :wordId")
    fun getSingleWordDetailsFlow(wordId: Int, reviewBlockId: Int): Flow<WordWithDetails>

    /**
    * Compound DAO function to save API word data in the database
    * */
    @Transaction
    suspend fun insertApiResponse(wordDetails: WordDetails, wordMeanings:List<WordMeaning>) {
        insertWordDetail(wordDetails)
        insertWordMeanings(wordMeanings)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordDetail(wordDetails: WordDetails)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordMeanings(wordMeaning:List<WordMeaning>)

    @Query("SELECT d.word_id " +
            "FROM word_details AS d " +
            "WHERE d.word_id IN (:wordIds)")
    suspend fun isWordsCached(wordIds: List<Int>): List<Int>

    @Query("SELECT w.id, w.word, w.part_of_speech " +
            "FROM oxford_words AS w " +
            "LEFT JOIN word_block_oxford_words AS j " +
            "ON w.id = j.word_id " +
            "WHERE j.word_block_id = :blockId")
    fun getPickQuizWordFlow(blockId: Int): Flow<List<PickQuizWordsData>>

    @Query("""
        SELECT * FROM oxford_words 
        WHERE id NOT IN (
            SELECT word_id FROM word_block_oxford_words 
            WHERE word_block_id != :reviewBlockId
        ) 
        AND id NOT IN (:avoidIds)
        ORDER BY RANDOM() 
        LIMIT :limit
    """)
    suspend fun getRandomWords(limit: Int, reviewBlockId: Int, avoidIds: List<Int>): List<OxfordWords>
}