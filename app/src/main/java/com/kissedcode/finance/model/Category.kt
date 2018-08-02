package com.kissedcode.finance.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Category constructor(
        @field:PrimaryKey
        val ID_category: Int,
        val name: String,
        val type: OperationType
)
