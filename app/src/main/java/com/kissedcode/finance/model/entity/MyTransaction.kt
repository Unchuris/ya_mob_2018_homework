package com.kissedcode.finance.model.entity
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.Date

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
data class MyTransaction(
        @PrimaryKey(autoGenerate = true) var myTransactionId: Int? = null,
        val myTransactionDate: Date,
        val myTransactionAmount: Double,
        val categoryID: Int,
        val currencyID: Int,
        val walletID: Int
)
