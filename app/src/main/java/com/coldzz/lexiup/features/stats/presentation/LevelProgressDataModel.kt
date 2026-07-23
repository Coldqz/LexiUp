package com.coldzz.lexiup.features.stats.presentation

import com.coldzz.lexiup.core.common.CerfLevel

data class LevelProgressDataModel(
    val level: CerfLevel = CerfLevel.Unknown,
    val percentage: Float = 0f
)