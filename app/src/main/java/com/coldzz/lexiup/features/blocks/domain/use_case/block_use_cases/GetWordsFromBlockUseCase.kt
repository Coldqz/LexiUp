package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.projection.BlockWordsListData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsFromBlockUseCase @Inject constructor(
    val blockRepository: WordBlockRepository
) {
    operator fun invoke(blockId: Int): Flow<BlockWordsListData> {
        return blockRepository.getWordsFromBlock(blockId)
    }
}