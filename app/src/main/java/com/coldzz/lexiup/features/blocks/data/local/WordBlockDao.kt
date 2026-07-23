package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.blocks.data.local.projection.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WordBlockDao {
    @Query("SELECT * FROM word_block")
    fun getAllBlocks(): Flow<List<WordBlock>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordsToBlock(wordBlockOxfordWordsList: List<WordBlockOxfordWords>)

    @Delete
    suspend fun deleteWordFromBlock(wordBlockOxfordWordsList: List<WordBlockOxfordWords>)

    @Query("SELECT * FROM word_block")
    fun getWordBlockWithOxfordWords(): Flow<List<WordBlockWithOxfordWords>>

    @Insert
    suspend fun addBlock(wordBlock: WordBlock): Long

    @Query("DELETE FROM word_block WHERE id = :blockId")
    suspend fun deleteBlock(blockId: Int)

    @Transaction
    suspend fun createBlockWithWords(wordBlock: WordBlock, wordIds: List<Int>) {
        val blockId = addBlock(wordBlock).toInt()
        val formattedList = wordIds.map {
            WordBlockOxfordWords(
                blockId,
                wordId = it
            )
        }
        addWordsToBlock(formattedList)
    }

    @Query("SELECT MAX(block_number) FROM word_block")
    suspend fun getMaxBlockNumber(): Int

    @Query("SELECT id FROM word_block WHERE isPermanent = 1 LIMIT 1")
    suspend fun getReviewBlockId(): Int

    @Query("UPDATE word_block SET block_type = :blockType WHERE id = :blockId")
    suspend fun updateBlockType(blockId: Int, blockType: BlockTypes)

    @Query(
        "SELECT b.*, w.*, " +
                "CASE WHEN j.word_block_id IS NULL THEN 0 ELSE 1 END AS isInReviewBlock " +
                "FROM oxford_words AS w " +
                "JOIN word_block_oxford_words AS j " +
                "ON w.id = j.word_id " +
                "JOIN word_block AS b " +
                "ON b.id = j.word_block_id " +
                "WHERE j.word_block_id = :blockId"
    )
    fun getWordsFromBlock(blockId: Int): Flow<Map<WordBlock, List<WordsWithReviewBlockIndicator>>>

    @Query("UPDATE word_block SET available_at = :availableAt WHERE id = :blockId")
    suspend fun updateBlockAvailability(blockId: Int, availableAt: LocalDate?)

    /**
    * Return how many blocks exists of specific type. Also, we need to filter out reviewBlock
     * so we add [isPermanent] parameter which is set to false just to use in the query
    * */
    @Query("SELECT COUNT(*) FROM word_block WHERE block_type = :blockType AND isPermanent = :isPermanent")
    suspend fun getSpecificBlockTypeTotalNumber(blockType: BlockTypes, isPermanent: Boolean = false): Int


    // update progress block information
    @Query(
        "UPDATE word_block " +
                "SET learning_level = :learningLevelIndicator, available_at = :availableAt, completed_at = :completedAt " +
                "WHERE id = :blockId"
    )
    suspend fun updateLearningProgress(blockId: Int, learningLevelIndicator: LearningLevelIndicator, availableAt: LocalDate?, completedAt: LocalDate?)

    @Query("SELECT learning_level FROM word_block WHERE id = :blockId")
    suspend fun getBlockLearningLevel(blockId: Int): LearningLevelIndicator
}