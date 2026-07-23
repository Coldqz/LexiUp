package com.coldzz.lexiup.features.blocks.domain.use_case

import com.coldzz.lexiup.features.blocks.domain.AvailabilityLabels
import java.time.LocalDate
import javax.inject.Inject

class GenerateAvailableAtLabelUseCase @Inject constructor() {
    operator fun invoke(availableAt: LocalDate?): AvailabilityLabels {
        val todayDate = LocalDate.now()
        // This function decide what label block should have.
        return when {
            availableAt == null -> AvailabilityLabels.Empty
            availableAt == todayDate -> AvailabilityLabels.Today
            availableAt == todayDate.plusDays(1) -> AvailabilityLabels.Tomorrow
            // if user is late block will stay available today, then algorithm will handle blocks order
            availableAt.isBefore(todayDate) -> AvailabilityLabels.Today
            else -> {
                val days = availableAt.toEpochDay() - todayDate.toEpochDay()
                AvailabilityLabels.InDays(days.toInt())
            }
        }
    }
}