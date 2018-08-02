package com.kissedcode.finance.model
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Wallet::class,
        parentColumns = arrayOf("ID_wallet"),
        childColumns = arrayOf("walletID"),
        onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Currency::class,
        parentColumns = arrayOf("ID_currency"),
        childColumns = arrayOf("currencyID")),
        ForeignKey(entity = Category::class,
        parentColumns = arrayOf("ID_category"),
        childColumns = arrayOf("categoryID"))))

data class Transaction(
        @field:PrimaryKey
        val ID_transaction: Int,
        val date: Date,
        val amount: Double,
        val categoryID: Int,
        val currencyID: Int,
        val walletID: Int
)
