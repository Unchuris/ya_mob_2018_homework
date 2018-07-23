package com.kissedcode.finance.model

enum class Currency(val cost: Double = 1.0) {
    USD,
    RUB(1/60.5)
}

class MoneyOperation(val type: Type, val value: Double, val currency: Currency = Currency.USD) {

    enum class Type {
        PLUS,
        MINUS
    }
}