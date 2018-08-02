package com.kissedcode.finance.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Category (
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        val name: String,
        val type: OperationType
)
