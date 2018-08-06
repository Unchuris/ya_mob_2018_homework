package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.kissedcode.finance.model.OperationType

@Entity
data class Category (
    @PrimaryKey(autoGenerate = true) var categoryId: Int? = null,
    val categoryName: String,
    val categoryType: OperationType
)
