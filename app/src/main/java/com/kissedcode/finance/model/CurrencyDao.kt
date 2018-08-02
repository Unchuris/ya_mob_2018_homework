package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CurrencyDao {
    @get:Query("SELECT * FROM currency")
    val all: List<Currency>

    @Insert
    fun insertAll(vararg users: Currency)
}
