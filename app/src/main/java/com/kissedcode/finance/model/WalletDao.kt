package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface WalletDao {
    @get:Query("SELECT * FROM wallet")
    val all: List<Wallet>

    @Insert
    fun insertAll(vararg users: Wallet)
}
