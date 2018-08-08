package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.kissedcode.finance.model.entity.IdleWallet
import com.kissedcode.finance.model.entity.Wallet
import io.reactivex.Flowable

@Dao
interface IdleWalletDao {

    @Delete
    fun delete(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Query("SELECT wallet.walletId as IdleWalletId, walletName, walletValue, Currency.*FROM wallet INNER JOIN CURRENCY ON wallet.currencyID = Currency.currencyId")
    fun getAll(): Flowable<List<IdleWallet>>
}
