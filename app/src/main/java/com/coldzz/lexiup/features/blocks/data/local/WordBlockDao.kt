package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockWithOxfordWords
import kotlinx.coroutines.flow.Flow

@Dao
interface WordBlockDao {
    // TODO: check if we need this at all
    @Query("SELECT * FROM word_block")
    fun getAllBlocks(): Flow<List<WordBlock>>

    @Insert
    suspend fun addWordsToBlock(wordBlockOxfordWordsList: List<WordBlockOxfordWords>)

    @Transaction
    @Query("SELECT * FROM word_block")
    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    @Insert
    suspend fun addBlock(wordBlock: WordBlock)
}