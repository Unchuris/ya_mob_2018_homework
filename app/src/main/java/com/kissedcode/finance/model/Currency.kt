package com.kissedcode.finance.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Currency constructor(
        @field:PrimaryKey
        val ID_currency: Int,
        val name: String,
        val symbol: String,
        val standardName: String
)
