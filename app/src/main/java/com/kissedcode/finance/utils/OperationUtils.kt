package com.kissedcode.finance.utils

import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.IdleTransaction
import com.kissedcode.finance.repository.Rate

fun convert(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double {
    return amount * rate(fromCurrency, toCurrency)
}

private fun rate(fromCurrency: Currency, toCurrency: Currency): Double {
    if (fromCurrency == toCurrency) return 1.00
    return if (fromCurrency.standardName == "RUB" && toCurrency.standardName == "USD") (1.00 / Rate.rate) else Rate.rate
}

fun sumOperations(transactions: List<IdleTransaction>, currency: Currency): Double {
    var amount = 0.0
    var sum = 0.0
    for (transaction in transactions) {
        amount += if (currency == transaction.currency) transaction.idleTransactionAmount else convert(transaction.idleTransactionAmount, transaction.currency, currency)
        if (transaction.category.categoryType === OperationType.INCOME) sum += amount else sum -= amount
        amount = 0.0
    }
    return sum
}
