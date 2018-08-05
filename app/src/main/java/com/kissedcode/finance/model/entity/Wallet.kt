package com.kissedcode.finance.model.entity
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(foreignKeys = [(ForeignKey(entity = Currency::class,
        parentColumns = arrayOf("currencyId"),
        childColumns = arrayOf("currencyID")))])
data class Wallet(
        @PrimaryKey(autoGenerate = true) var walletId: Int? = null,
        val walletName: String,
        var walletValue: Double,
        val currencyID: Int
)
