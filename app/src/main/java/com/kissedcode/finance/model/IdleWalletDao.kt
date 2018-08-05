package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.IdleWallet
import io.reactivex.Flowable

@Dao
interface IdleWalletDao {

    @Query("SELECT wallet.walletId as IdleWalletId, walletName, walletValue, Currency.*FROM wallet INNER JOIN CURRENCY ON wallet.currencyID = Currency.currencyId")
    fun getAll(): Flowable<List<IdleWallet>>

}
