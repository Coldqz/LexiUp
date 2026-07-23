package com.coldzz.lexiup.features.stats.data.local.repository

import com.coldzz.lexiup.features.blocks.data.local.projection.ProgressByCerfLevel
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getLearnedWordsNumberFromAllBlocks(): Flow<Int>

    fun getTotalNumberOfWordsByCerfLevels(): Flow<List<ProgressByCerfLevel>>
}