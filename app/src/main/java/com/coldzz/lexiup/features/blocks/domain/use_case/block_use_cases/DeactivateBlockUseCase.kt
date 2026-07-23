package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import javax.inject.Inject

class DeactivateBlockUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository
) {
    suspend operator fun invoke(blockId: Int) {
        blockRepository.deactivateBlock(blockId)
    }
}