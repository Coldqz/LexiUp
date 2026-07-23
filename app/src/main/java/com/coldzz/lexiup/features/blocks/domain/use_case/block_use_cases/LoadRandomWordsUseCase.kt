package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import javax.inject.Inject

class LoadRandomWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
    private val wordBlockRepository: WordBlockRepository
) {

    /**
    * Function query random words from the db.
     *
     * Use [randomWordsCount] to adjust the number.
     * [wordIdsToAvoid] is to ensure uniqueness, function query random words and also avoid those which are in the list.
     * When we add new words to already existing we need to ensure uniqueness,
     * but when we run function for the first time we have no words on our screen
     * so we don't need to avoid any ids, in that case leave [wordIdsToAvoid] null.
    * */
    suspend operator fun invoke(randomWordsCount: Int, wordIdsToAvoid: List<Int>? = null): List<OxfordWords> {
        // Get review block ID to allow words from it to be picked again
        val reviewBlockId = wordBlockRepository.getCachedReviewBlockId()

        // Prepare list of IDs to exclude (must not be empty for Room's NOT IN)
        val idsToExclude = if (wordIdsToAvoid.isNullOrEmpty()) listOf(-1) else wordIdsToAvoid

        // Return random words using efficient SQL query
        return wordRepository.getRandomWords(
            limit = randomWordsCount,
            reviewBlockId = reviewBlockId,
            avoidIds = idsToExclude
        )
    }
}