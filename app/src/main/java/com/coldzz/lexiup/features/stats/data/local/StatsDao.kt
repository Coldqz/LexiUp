package com.coldzz.lexiup.features.stats.data.local

import androidx.room.Dao
import androidx.room.Query
import com.coldzz.lexiup.features.blocks.data.local.projection.ProgressByCerfLevel
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {
    // Return number of learned words
    @Query("SELECT COUNT(DISTINCT (w.id)) " +
            "FROM oxford_words as w " +
            "JOIN word_block_oxford_words as j " +
            "ON w.id =  j.word_id " +
            "JOIN word_block AS b " +
            "ON b.id = j.word_block_id " +
            "WHERE b.block_type = :blockType")
    fun getLearnedWordsNumberFromAllBlocks(blockType: BlockTypes = BlockTypes.LEARNED): Flow<Int>

    // return total number of words grouped by Cerf level, also how many of then are learned
    @Query("SELECT w.level, " +
            "COUNT(DISTINCT w.id) AS total_words, " +
            "COUNT(DISTINCT CASE WHEN b.block_type = :blockType THEN w.id END) AS learned_words " +
            "FROM oxford_words as w " +
            "LEFT JOIN word_block_oxford_words AS j " +
            "ON w.id = j.word_id " +
            "LEFT JOIN word_block AS b " +
            "ON b.id = j.word_block_id " +
            "GROUP BY w.level")
    fun getWordNumbersByCerfLevel(blockType: BlockTypes = BlockTypes.LEARNED): Flow<List<ProgressByCerfLevel>>
}