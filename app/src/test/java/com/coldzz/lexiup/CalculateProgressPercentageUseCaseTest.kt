package com.coldzz.lexiup

import com.coldzz.lexiup.features.stats.domain.CalculateProgressPercentageUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateProgressPercentageUseCaseTest {

    private lateinit var useCase: CalculateProgressPercentageUseCase

    @Before
    fun setUp() {
        useCase = CalculateProgressPercentageUseCase()
    }

    @Test
    fun `should return exact percentage when math splits evenly`() {
        val currentlyLearned = 1
        val total = 4

        val result = useCase(currentlyLearned, total)

        // 1 / 4 * 100 = 25.00
        assertEquals(25.00f, result)
    }

    @Test
    fun `should round down when third decimal place is less than 5`() {
        val currentlyLearned = 1
        val total = 3 // 1 / 3 = 33.33333...

        val result = useCase(currentlyLearned, total)

        assertEquals(33.33f, result)
    }

    @Test
    fun `should round up when third decimal place is 5 or greater`() {
        val currentlyLearned = 2
        val total = 3 // 2 / 3 = 66.66666...

        val result = useCase(currentlyLearned, total)

        assertEquals(66.67f, result)
    }

    @Test
    fun `should return 100 when all words are learned`() {
        val currentlyLearned = 87
        val total = 87

        val result = useCase(currentlyLearned, total)

        assertEquals(100.00f, result)
    }

    @Test
    fun `should return 0 when no words are learned`() {
        val currentlyLearned = 0
        val total = 50

        val result = useCase(currentlyLearned, total)

        assertEquals(0.00f, result)
    }
}