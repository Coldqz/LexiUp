package com.coldzz.lexiup.features.user.data.local.repository

import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UserRepository {

    fun getUserFlow(): Flow<UserStats>

    suspend fun getBlocksLearnedToday(): Int?

    suspend fun increaseBlocksLearnedToday(newBlock: Boolean)

    suspend fun getLastUpdateDate(): LocalDate?
}