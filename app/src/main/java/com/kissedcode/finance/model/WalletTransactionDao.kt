package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.kissedcode.finance.model.entity.MyTransaction

@Dao
abstract class WalletTransactionDao {

    @Insert
    abstract fun insertFinanceOperation(transaction: MyTransaction)

    @Query("UPDATE wallet SET walletValue = walletValue + :value WHERE walletId = :id")
    abstract fun updateWallets(id: Int, value: Double)

    @Transaction
    open fun insertTransactionAndUpdateWallet(transaction: MyTransaction, walletID: Int, value: Double) {
        insertFinanceOperation(transaction)
        updateWallets(walletID, value)
    }
}