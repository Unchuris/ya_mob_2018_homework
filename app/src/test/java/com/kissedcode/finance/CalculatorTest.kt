package com.kissedcode.finance

import com.kissedcode.finance.model.Calculator
import com.kissedcode.finance.model.MoneyOperation.Type.*
import com.kissedcode.finance.model.Currency.*
import com.kissedcode.finance.model.MoneyOperation

import org.junit.Assert.*
import org.junit.Test

class CalculatorTest {

    val calculator = Calculator()

    @Test
    fun `calculator summarize right`() {

        var actual: Double
        var expected: Double
        val delta = 0.0

        // 100.55 USD + 50.20 USD - 150.75 USD = 0 USD
        expected = 0.0
        actual = calculator.sum(
                MoneyOperation(PLUS, 100.55, USD),
                MoneyOperation(PLUS, 50.20, USD),
                MoneyOperation(MINUS, 150.75, USD)
        )

        assertEquals(expected, actual, delta)

        // 100 USD + 50 USD - 605 RUB = 140 USD
        expected = 140.0
        actual = calculator.sum(
                MoneyOperation(PLUS, 100.0, USD),
                MoneyOperation(PLUS, 50.0, USD),
                MoneyOperation(MINUS, 605.0, RUB)
        )

        assertEquals(expected, actual, delta)


    }
}