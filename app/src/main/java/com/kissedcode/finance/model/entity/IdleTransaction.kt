package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey
import java.util.*

data class IdleTransaction (
        @PrimaryKey(autoGenerate = true) var IdleTransactionId: Int? = null,
        val idleTransactionDate: Date,
        val idleTransactionAmount: Double,
        @Embedded
        val category: Category,
        @Embedded
        val currency: Currency,
        @Embedded
        val wallet: Wallet
)
