package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.MyTransaction

@Dao
interface TransactionDao {
    @get:Query("SELECT * FROM myTransaction")
    val all: List<MyTransaction>

    @Insert
    fun insertAll(vararg users: MyTransaction)
}