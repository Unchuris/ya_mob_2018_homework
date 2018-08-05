package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [
    (ForeignKey(entity = Wallet::class,
            parentColumns = arrayOf("walletId"),
            childColumns = arrayOf("walletID"),
            onDelete = ForeignKey.CASCADE)),
    (ForeignKey(entity = Currency::class,
            parentColumns = arrayOf("currencyId"),
            childColumns = arrayOf("currencyID"))),
    (ForeignKey(entity = Category::class,
            parentColumns = arrayOf("categoryId"),
            childColumns = arrayOf("categoryID")))])
data class IdleDeferTransaction(
        @PrimaryKey(autoGenerate = true) var idleDeferTransactionId: Int? = null,
        val idleDeferTransactionDate: Date,
        val idleDeferTransactionAmount: Double,
        val categoryID: Int,
        val currencyID: Int,
        val walletID: Int,
        var nextRepeatDay: Int,
        var nextRepeatMonth: Int,
        var nextRepeatYear: Int,
        var repeatDays: Int
)