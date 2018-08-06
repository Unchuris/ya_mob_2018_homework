package com.kissedcode.finance.model.database

import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.Wallet

fun initWallet(): List<Wallet> = arrayListOf(
        Wallet(1, "Card", 00.00, 2),
        Wallet(2, "Credit card", 00.00, 1),
        Wallet(3, "Debit card", 00.00, 2))

fun initCurrency(): List<Currency> = arrayListOf(
        Currency(1, "Ruble", "₽", "RUB"),
        Currency(2, "Dollar", "$", "USD")
)

fun initCategory(): List<Category> = arrayListOf(
        Category(1, "Food", OperationType.SPEND),
        Category(2, "Clothes", OperationType.SPEND),
        Category(3, "Service", OperationType.SPEND),
        Category(4, "Sport", OperationType.SPEND),
        Category(5, "House", OperationType.SPEND),
        Category(6, "Relaxation", OperationType.SPEND),
        Category(7, "Utilities", OperationType.SPEND),
        Category(8, "Other expenses", OperationType.SPEND),
        Category(9, "Salary", OperationType.INCOME),
        Category(10, "Refund", OperationType.INCOME),
        Category(11, "Other income", OperationType.INCOME)
)
