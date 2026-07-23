package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import javax.inject.Inject

class ActivateBlockUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository
) {
    /**
     * Function checks if it is possible to add more active blocks if yes then adds and return success,
     * if max count of active blocks was exceeded then do nothing and return IllegalStateException
     * */
    suspend operator fun invoke(blockId: Int): Result<Unit> {
        return if (blockRepository.activateBlockIfPossible(blockId)) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException())
        }
    }
}