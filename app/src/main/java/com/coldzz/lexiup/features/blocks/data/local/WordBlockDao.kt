package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.entities.WordPreviewDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface WordBlockDao {
    // TODO: check if we need this at all
    @Query("SELECT * FROM word_block")
    fun getAllBlocks(): Flow<List<WordBlock>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordsToBlock(wordBlockOxfordWordsList: List<WordBlockOxfordWords>)

    @Delete
    suspend fun deleteWordFromBlock(wordBlockOxfordWordsList: List<WordBlockOxfordWords>)

    @Query("SELECT * FROM word_block")
    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    @Insert
    suspend fun addBlock(wordBlock: WordBlock)

    @Query("SELECT id FROM word_block WHERE isPermanent = 1 LIMIT 1")
    suspend fun getReviewBlockId(): Int

    @Transaction
    @Query(
        "SELECT w.word, w.part_of_speech, w.level " +
                "FROM oxford_words AS w " +
                "JOIN word_block_oxford_words AS j " +
                "ON w.id = j.word_id " +
                "WHERE j.word_block_id = :blockId"
    )
    fun getWordPreviewDetailsFromBlock(blockId: Int): Flow<List<WordPreviewDetail>>
}