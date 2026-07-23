package com.coldzz.lexiup.features.words.domain.use_case

import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.projection.WordWithDetails
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordDetailsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
    private val blockRepository: WordBlockRepository
) {
    suspend operator fun invoke(wordId: Int): Flow<ResultDataState<WordWithDetails>> {
        return wordRepository.getSingleWordDetailsFlow(
            wordId = wordId,
            reviewBlockId = blockRepository.getCachedReviewBlockId()
        )
    }
}