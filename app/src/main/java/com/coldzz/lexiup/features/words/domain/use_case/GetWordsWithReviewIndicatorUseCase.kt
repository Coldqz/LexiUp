package com.coldzz.lexiup.features.words.domain.use_case

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsWithReviewIndicatorUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository,
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(): Flow<List<WordsWithReviewBlockIndicator>> {
        return wordRepository.getWordsAndReviewBlockIndicator(blockRepository.getCachedReviewBlockId())
    }
}