package com.coldzz.lexiup.features.blocks.presentation

import com.coldzz.lexiup.features.blocks.domain.AvailabilityLabels
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator

sealed class BlockItemUiModel {
    data class Active(
        val id: Int,
        val blockNumber: Int,
        val learningLevel: LearningLevelIndicator,
        val availableAt: AvailabilityLabels,
        val isLearnButtonActive: Boolean
    ): BlockItemUiModel()

    data class Planned(
        val id: Int,
        val blockNumber: Int,
    ): BlockItemUiModel()

    data class Learned(
        val id: Int,
        val blockNumber: Int,
        val completedAt: String
    ): BlockItemUiModel()
}