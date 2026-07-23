package com.coldzz.lexiup.features.stats.data.local.repository

import com.coldzz.lexiup.features.blocks.data.local.projection.ProgressByCerfLevel
import com.coldzz.lexiup.features.stats.data.local.StatsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(private val dao: StatsDao) : StatsRepository {
    override fun getLearnedWordsNumberFromAllBlocks(): Flow<Int> {
        return dao.getLearnedWordsNumberFromAllBlocks()
    }

    override fun getTotalNumberOfWordsByCerfLevels(): Flow<List<ProgressByCerfLevel>> {
        return dao.getWordNumbersByCerfLevel()
    }
}
