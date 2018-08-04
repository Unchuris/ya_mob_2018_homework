package com.kissedcode.finance.utils

import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.repository.Rate

fun convert(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double {
       return amount * rate(fromCurrency, toCurrency)
}

private fun rate(fromCurrency: Currency, toCurrency: Currency): Double {
    if (fromCurrency == toCurrency) return 1.00
    return if (fromCurrency.standardName == "RUB" && toCurrency.standardName == "USD") (1.00 / Rate.rate) else Rate.rate
}
