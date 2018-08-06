package com.kissedcode.finance.utils

import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.IdleTransaction
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.repository.Rate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Date

class OperationTest {

    private lateinit var list: List<IdleTransaction>
    private lateinit var currency: List<Currency>
    private val rate = Rate.rate

    @Before
    fun setUp() {
        currency = arrayListOf(
                Currency(1, "Ruble", "₽", "RUB"),
                Currency(2, "Dollar", "$", "USD")
        )
        list = listOf(IdleTransaction(0, Date(), 22.01,
                        Category(1, "Food", OperationType.SPEND),
                        currency[0],
                        Wallet(1, "Card", 00.00, 2)),
                        IdleTransaction(1, Date(), 54.01,
                                Category(9, "Salary", OperationType.INCOME),
                                currency[1],
                                Wallet(2, "Credit card", 10.00, 1))
        )
    }

    @Test
    fun testDollarToRubleConvert() {
        val rez:Double = 100 * rate
        val my:Double = convert(100.00, currency[1], currency[0])
        Assert.assertEquals(rez, my, 0.0)
    }

    @Test
    fun testRubleToDollarConvert() {
        val rez:Double = 100 / rate
        val my:Double = convert(100.00, currency[0], currency[1])
        Assert.assertEquals(rez, my, 0.0)
    }

    @Test
    fun testRubleToDollarConvert2() {
        val rez:Double = -100 / rate
        val my:Double = convert(-100.00, currency[0], currency[1])
        Assert.assertEquals(rez, my, 0.0)
    }

    @Test
    fun testRubleToDollarConvert3() {
        val rez:Double = 0 / rate
        val my:Double = convert(0.0, currency[0], currency[1])
        Assert.assertEquals(rez, my, 0.0)
    }

    @Test
    fun testSUMtoRuble() {
        val rez:Double = -22.01 + 54.01 * rate
        val my:Double = sumOperations(list, currency[0])
        Assert.assertEquals(rez, my, 0.0)
    }

    @Test
    fun testSUMtoDollar() {
        val rez:Double = -22.01 / rate + 54.01
        val my:Double = sumOperations(list, currency[1])
        Assert.assertEquals(rez, my, 0.00)
    }

}
