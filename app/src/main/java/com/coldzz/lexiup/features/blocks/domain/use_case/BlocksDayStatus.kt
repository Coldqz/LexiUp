package com.coldzz.lexiup.features.blocks.domain.use_case

sealed class BlocksDayStatus {
    data object Rest: BlocksDayStatus()
    data object DailyLimitReached: BlocksDayStatus()
    data object NewBlockDailyLimitReached: BlocksDayStatus()
    data object CanLearn: BlocksDayStatus()
}