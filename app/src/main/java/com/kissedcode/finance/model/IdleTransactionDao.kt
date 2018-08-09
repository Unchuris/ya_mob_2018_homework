package com.kissedcode.finance.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.IdleTransaction
import com.kissedcode.finance.model.entity.MyTransaction
import io.reactivex.Flowable
import java.util.Date

@Dao
interface IdleTransactionDao {

    @Delete
    fun delete(operation: MyTransaction)

    @Query("SELECT MyTransaction.myTransactionId as IdleTransactionId, MyTransaction.myTransactionDate as idleTransactionDate, MyTransaction.myTransactionAmount as idleTransactionAmount, Category.*, Wallet.*, Currency.*FROM MyTransaction INNER JOIN Category ON MyTransaction.categoryID = Category.categoryId INNER JOIN WALLET ON MyTransaction.walletID = Wallet.walletId INNER JOIN CURRENCY ON MyTransaction.currencyID = Currency.currencyId WHERE template = 0")
    fun getAll(): Flowable<List<IdleTransaction>>

    @Query("SELECT MyTransaction.myTransactionId as IdleTransactionId, MyTransaction.myTransactionDate as idleTransactionDate, MyTransaction.myTransactionAmount as idleTransactionAmount, Category.*, Wallet.*, Currency.*FROM MyTransaction INNER JOIN Category ON MyTransaction.categoryID = Category.categoryId INNER JOIN WALLET ON MyTransaction.walletID = Wallet.walletId INNER JOIN CURRENCY ON MyTransaction.currencyID = Currency.currencyId WHERE template = 1")
    fun getAllTemplates(): Flowable<List<IdleTransaction>>

    @Query("""SELECT MyTransaction.myTransactionId as IdleTransactionId,
                            MyTransaction.myTransactionDate as idleTransactionDate,
                            MyTransaction.myTransactionAmount as idleTransactionAmount,
                            Category.*,Wallet.*, Currency.*
        FROM MyTransaction
        INNER JOIN Category ON MyTransaction.categoryID = Category.categoryId
        INNER JOIN WALLET ON MyTransaction.walletID = Wallet.walletId
        INNER JOIN CURRENCY ON MyTransaction.currencyID = Currency.currencyId
        WHERE template = 0 and
                MyTransaction.myTransactionDate <= :dateTo and
                MyTransaction.myTransactionDate >= :dateFrom and
                Category.categoryType = :type GROUP BY Category.categoryId""")
    fun getFilterByDateInterval(dateFrom: Date, dateTo: Date, type: OperationType): Flowable<List<IdleTransaction>>
}
