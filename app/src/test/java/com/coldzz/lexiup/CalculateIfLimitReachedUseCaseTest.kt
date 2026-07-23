package com.coldzz.lexiup

import com.coldzz.lexiup.features.blocks.domain.use_case.CalculateIfDailyLimitReachedUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class CalculateIfLimitReachedUseCaseTest {
    val useCase = CalculateIfDailyLimitReachedUseCase()

    @Test
    fun `should return false when input are nulls `(){
        val result = useCase(null, null)
        assertEquals(false, result)
    }

    @Test
    fun `should return false when lastUpdateDate is before today`(){
        val today = LocalDate.now()

        val result1 = useCase(2, today.minusDays(1))
        val result2 = useCase(2, today.minusDays(2))
        val result3 = useCase(2, today.minusDays(10))

        assertEquals(false, result1)
        assertEquals(false, result2)
        assertEquals(false, result3)
    }

    @Test
    fun `should return false when lastUpdateDate is today and blocks learned 1`(){
        val today = LocalDate.now()

        val result = useCase(1, today)

        assertEquals(false, result)
    }

    @Test
    fun `should return true when lastUpdateDate is today and blocks learned 2`(){
        val today = LocalDate.now()

        val result = useCase(2, today)

        assertEquals(true, result)
    }

    @Test
    fun `should return false when lastUpdateDate is today and blocks learned 0`(){
        val today = LocalDate.now()

        val result = useCase(0, today)

        assertEquals(false, result)
    }
}