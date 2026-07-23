package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.features.user.data.local.repository.UserRepository
import javax.inject.Inject

class IncreaseBlockLearnedTodayUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(newBlock: Boolean) {
        userRepository.increaseBlocksLearnedToday(newBlock)
    }
}