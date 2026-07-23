package com.coldzz.lexiup.features.stats.domain

import javax.inject.Inject

class StatsUseCases @Inject constructor(
    val calculateProgressPercentageUseCase: CalculateProgressPercentageUseCase
)