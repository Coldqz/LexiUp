package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import javax.inject.Inject

class DeleteBlockUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository
) {
    suspend operator fun invoke(wordId: Int) {
        blockRepository.deleteWordBlock(wordId)
    }
}