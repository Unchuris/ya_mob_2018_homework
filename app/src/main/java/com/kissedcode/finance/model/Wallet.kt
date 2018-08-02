package com.kissedcode.finance.model
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(entity = Currency::class,
        parentColumns = arrayOf("ID_currency"),
        childColumns = arrayOf("currencyID")))])
data class Wallet(
        @field:PrimaryKey
        val ID_wallet: Int,
        val name: String,
        var value: Double,
        val currencyID: Int)
