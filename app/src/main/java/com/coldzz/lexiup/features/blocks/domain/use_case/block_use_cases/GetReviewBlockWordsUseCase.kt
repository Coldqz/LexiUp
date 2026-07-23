package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.projection.BlockWordsListData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewBlockWordsUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository
) {
    suspend operator fun invoke(): Flow<BlockWordsListData> {
        return blockRepository.getWordsFromReviewBlock()
    }
}