package com.kissedcode.finance.utils

import com.kissedcode.finance.model.Converters
import com.kissedcode.finance.model.OperationType
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.Calendar

class ConvertersTest {

    val converters = Converters()

    @Test
    fun testOperationTypeFromOrdinal() {
        assertEquals(converters.operationTypeFromOrdinal(1), OperationType.INCOME)
    }

    @Test
    fun testOperationTypeFromOrdinal1() {
        assertEquals(converters.operationTypeFromOrdinal(0), OperationType.SPEND)
    }

    @Test
    fun testOperationTypeToOrdinal() {
        assertEquals(converters.operationTypeToOrdinal(OperationType.SPEND), 0)
    }

    @Test
    fun testOperationTypeToOrdinal1() {
        assertEquals(converters.operationTypeToOrdinal(OperationType.INCOME), 1)
    }

    @Test
    fun testDateFromTimestamp() {
        val calendar = Calendar.getInstance()
        assertEquals(converters.dateFromTimestamp(calendar.timeInMillis), calendar.time)
    }

    @Test
    fun testDateToTimestamp() {
        val calendar = Calendar.getInstance()
        assertEquals(converters.dateToTimestamp(calendar.time), calendar.timeInMillis)
    }
}
