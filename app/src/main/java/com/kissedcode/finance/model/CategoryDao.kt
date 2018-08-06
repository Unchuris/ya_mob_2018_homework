package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.Category

@Dao
interface CategoryDao {
    @get:Query("SELECT * FROM category")
    val all: List<Category>

    @Insert
    fun insertAll(vararg users: Category)
}
