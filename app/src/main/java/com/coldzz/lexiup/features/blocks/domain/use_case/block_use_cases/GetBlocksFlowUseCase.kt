package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.blocks.domain.use_case.BuildBlocksUiStateUseCase
import com.coldzz.lexiup.features.blocks.presentation.BlocksScreenUiState
import com.coldzz.lexiup.features.user.data.local.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/*
* Here we combine two flows for blocks screen and return new flow with blocks state
* */
class GetBlocksFlowUseCase @Inject constructor(
    private val blockRepository: WordBlockRepository,
    private val userRepository: UserRepository,
    private val buildBlocksUiState: BuildBlocksUiStateUseCase
) {
    operator fun invoke(): Flow<BlocksScreenUiState> {
        return combine(
            blockRepository.getAllBlocks(),
            userRepository.getUserFlow()
        ) { blocks, stats ->
            buildBlocksUiState(
                blocks, stats
            )
        }
    }
}