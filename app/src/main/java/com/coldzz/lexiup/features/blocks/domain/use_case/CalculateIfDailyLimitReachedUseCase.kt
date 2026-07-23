package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.core.common.Constants
import java.time.LocalDate
import javax.inject.Inject

// Calculate if user reached his daily limit
class CalculateIfDailyLimitReachedUseCase @Inject constructor() {
    operator fun invoke(blocksLearnedToday: Int?, lastUpdateDate: LocalDate?): Boolean {
        val today = LocalDate.now()

        // If values are nulls then user hasn't learned any blocks yet
        val blocksLearnedToday = blocksLearnedToday ?: 0
        val lastUpdateDate = lastUpdateDate ?: today

        val wordsLeft = calculateWordsLeftForToday(
            lastUpdateDate = lastUpdateDate,
            blocksLearnedToday = blocksLearnedToday,
            todayDate = today)

        return wordsLeft <= 0
    }

    private fun calculateWordsLeftForToday(lastUpdateDate: LocalDate?, todayDate: LocalDate, blocksLearnedToday: Int): Int {

        val leftForToday =  if (lastUpdateDate == todayDate) {
            (Constants.MAX_LEARNED_BLOCKS_BY_DAY - blocksLearnedToday).coerceAtLeast(0)
        } else Constants.MAX_LEARNED_BLOCKS_BY_DAY

        return leftForToday
    }
}