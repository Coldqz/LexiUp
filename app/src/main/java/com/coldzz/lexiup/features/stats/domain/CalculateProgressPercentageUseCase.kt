package com.coldzz.lexiup.features.stats.domain

import java.util.Locale
import javax.inject.Inject

class CalculateProgressPercentageUseCase @Inject constructor() {
    operator fun invoke(currentlyLearnedWords: Int, totalWords: Int): Float {
        val number = currentlyLearnedWords.toFloat() / (totalWords.toFloat() / 100f)

        val formattedNumber = String.format(Locale.getDefault(),"%.2f", number)
        return formattedNumber.toFloat()
    }
}