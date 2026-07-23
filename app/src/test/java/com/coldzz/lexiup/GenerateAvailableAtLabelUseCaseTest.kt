package com.coldzz.lexiup

import com.coldzz.lexiup.features.blocks.domain.AvailabilityLabels
import com.coldzz.lexiup.features.blocks.domain.use_case.GenerateAvailableAtLabelUseCase
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate
import kotlin.random.Random

class GenerateAvailableAtLabelUseCaseTest {
    private val generateLabelUseCase = GenerateAvailableAtLabelUseCase()
    private val todayDate = LocalDate.now()

    @Test
    fun `should return Empty when day is null`() {
        val result = generateLabelUseCase(null)
        assertEquals(AvailabilityLabels.Empty, result)
    }

    @Test
    fun `should return Today when day is today`() {
        val result = generateLabelUseCase(todayDate)
        assertEquals(AvailabilityLabels.Today, result)
    }

    @Test
    fun `should return Tomorrow when day is day+1`() {
        val result = generateLabelUseCase(todayDate.plusDays(1))
        assertEquals(AvailabilityLabels.Tomorrow, result)
    }

    @Test
    fun `should return (available in days) when day is more than +2`() {
        for (i in 0..10) {
            val day = Random.nextInt(2, 100)
            val result = generateLabelUseCase(todayDate.plusDays(day.toLong()))
            assertEquals(AvailabilityLabels.InDays(day), result)
        }
    }

    @Test
    fun `should return Today when day is less by any number`() {
        for (i in 1..10) {
            val day = Random.nextInt(1, 100)
            val result = generateLabelUseCase(todayDate.minusDays(day.toLong()))
            assertEquals(AvailabilityLabels.Today, result)
        }
    }
}