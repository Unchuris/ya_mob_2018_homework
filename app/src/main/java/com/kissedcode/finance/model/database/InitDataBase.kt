package com.kissedcode.finance.model.database

import com.kissedcode.finance.model.Currency
import com.kissedcode.finance.model.Wallet

fun initWallet(): Wallet {
    return Wallet(1, "CARD", 100.00, 1)
}

fun initCurrency(): Currency {
    return Currency(1, "RUBLE", "₽", "RUB")
}