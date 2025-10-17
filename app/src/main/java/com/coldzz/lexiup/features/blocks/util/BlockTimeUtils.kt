package com.coldzz.lexiup.features.blocks.util

import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.max

object BlockTimeUtils {
    /**
     * Used to check if current block is available at the moment
     * using time when it will be available [availableAt] and comparing it to current time
     */
    fun calculateRemainingTime(availableAt: LocalDateTime): RemainingTime {
        val remaining = Duration.between(LocalDateTime.now(), availableAt)
        val hours = max(0,remaining.toHours())
        val minutes = max(0, remaining.toMinutes() % 60)
        return RemainingTime(remaining, hours, minutes)
    }

    /**
     * Calculate if time has run out based on given remaining time [time]
     * */
    fun isTimeUp(time: RemainingTime): Boolean =
        (time.remainingTime.isNegative || time.remainingTime.isZero)
}

/**
 * Data class to save time parameters
 * */
data class RemainingTime(
    val remainingTime: Duration,
    val hours: Long,
    val minutes: Long
)