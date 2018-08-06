package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.Wallet
import io.reactivex.Flowable

@Dao
interface WalletDao {
    @get:Query("SELECT * FROM wallet")
    val all: Flowable<List<Wallet>>

    @Insert
    fun insertAll(vararg wallets: Wallet)
}
