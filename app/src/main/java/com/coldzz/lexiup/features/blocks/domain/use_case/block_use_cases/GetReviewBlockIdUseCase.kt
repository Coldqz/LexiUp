package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import javax.inject.Inject

class GetReviewBlockIdUseCase @Inject constructor(
    private val wordBlockRepository: WordBlockRepository
) {
    suspend operator fun invoke(): Int {
        return wordBlockRepository.getCachedReviewBlockId()
    }
}