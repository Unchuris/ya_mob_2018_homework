package com.kissedcode.finance.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Currency (
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        val name: String,
        val symbol: String,
        val standardName: String
)
