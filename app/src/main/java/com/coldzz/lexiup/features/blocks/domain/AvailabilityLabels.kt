package com.coldzz.lexiup.features.blocks.domain

sealed class AvailabilityLabels {
    object Today : AvailabilityLabels()
    object Tomorrow : AvailabilityLabels()
    data class InDays(val days: Int) : AvailabilityLabels()
    object Empty : AvailabilityLabels()
}