package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.core.common.Constants
import java.time.LocalDate
import javax.inject.Inject

/**
 * This useCase is to calculate if user reached new blocks for day limit. Current limit is 1 new block daily.
 * */
class CalculateIfNewBlocksDailyLimitReachedUseCase @Inject constructor() {
    operator fun invoke(newBlocksLearnedToday: Int?, lastUpdateDate: LocalDate?): Boolean {

        val today = LocalDate.now()

        // if those are nulls then user haven't learned any blocks yet, i.e. all limits are 0
        val lastUpdateDate = lastUpdateDate ?: today
        val newBlocksLearnedToday = newBlocksLearnedToday ?: 0

        val limitIsExceeded = newBlocksLearnedToday >= Constants.MAX_NEW_BLOCKS_LEARNED_BY_DAY
        return limitIsExceeded && lastUpdateDate.isEqual(today)
    }
}