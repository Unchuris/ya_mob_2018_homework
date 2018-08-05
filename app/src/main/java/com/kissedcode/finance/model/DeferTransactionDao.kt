package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.DeferTransaction
import com.kissedcode.finance.model.entity.IdleDeferTransaction

@Dao
interface DeferTransactionDao {

    @Query("SELECT IdleDeferTransaction.idleDeferTransactionId as deferTransactionId, IdleDeferTransaction.idleDeferTransactionDate as deferTransactionDate, IdleDeferTransaction.idleDeferTransactionAmount as deferTransactionAmount, IdleDeferTransaction.nextRepeatDay as nextRepeatDay, IdleDeferTransaction.nextRepeatMonth as nextRepeatMonth, IdleDeferTransaction.nextRepeatYear as nextRepeatYear, IdleDeferTransaction.repeatDays as repeatDays, Category.*, Wallet.*, Currency.*FROM IdleDeferTransaction INNER JOIN Category ON IdleDeferTransaction.categoryID = Category.categoryId INNER JOIN WALLET ON IdleDeferTransaction.walletID = Wallet.walletId INNER JOIN CURRENCY ON IdleDeferTransaction.currencyID = Currency.currencyId")
    fun getAll(): List<DeferTransaction>

    @Query("SELECT IdleDeferTransaction.idleDeferTransactionId as deferTransactionId, IdleDeferTransaction.idleDeferTransactionDate as deferTransactionDate, IdleDeferTransaction.idleDeferTransactionAmount as deferTransactionAmount, IdleDeferTransaction.nextRepeatDay as nextRepeatDay, IdleDeferTransaction.nextRepeatMonth as nextRepeatMonth, IdleDeferTransaction.nextRepeatYear as nextRepeatYear, IdleDeferTransaction.repeatDays as repeatDays, Category.*, Wallet.*, Currency.*FROM IdleDeferTransaction INNER JOIN Category ON IdleDeferTransaction.categoryID = Category.categoryId INNER JOIN WALLET ON IdleDeferTransaction.walletID = Wallet.walletId INNER JOIN CURRENCY ON IdleDeferTransaction.currencyID = Currency.currencyId WHERE nextRepeatDay = :day AND nextRepeatMonth = :month AND nextRepeatYear = :year")
    fun getByDate(day: Int, month: Int, year: Int): List<DeferTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg deferOperation: IdleDeferTransaction)

}
