package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey
import java.util.*

data class DeferTransaction(
        @PrimaryKey(autoGenerate = true) var deferTransactionId: Int? = null,
        val deferTransactionDate: Date,
        val deferTransactionAmount: Double,
        @Embedded
        val category: Category,
        @Embedded
        val currency: Currency,
        @Embedded
        val wallet: Wallet,
        var nextRepeatDay: Int,
        var nextRepeatMonth: Int,
        var nextRepeatYear: Int,
        var repeatDays: Int
)
