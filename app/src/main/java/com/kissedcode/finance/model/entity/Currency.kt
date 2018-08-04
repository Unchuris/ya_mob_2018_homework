package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Currency (
        @PrimaryKey(autoGenerate = true) var currencyId: Int? = null,
        val currencyName: String,
        val symbol: String,
        val standardName: String
)
