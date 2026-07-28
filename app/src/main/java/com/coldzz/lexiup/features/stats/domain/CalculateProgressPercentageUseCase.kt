package com.coldzz.lexiup.features.stats.domain

import javax.inject.Inject

class CalculateProgressPercentageUseCase @Inject constructor() {
    operator fun invoke(currentlyLearnedWords: Int, totalWords: Int): Float {
        if (totalWords == 0) return 0f
        val number = (currentlyLearnedWords.toFloat() / totalWords.toFloat()) * 100
        return kotlin.math.round(number * 100) / 100f
    }
}