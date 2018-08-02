package com.kissedcode.finance.model
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(entity = Currency::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("currencyID")))])
data class Wallet(
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        val name: String,
        var value: Double,
        val currencyID: Int
)
