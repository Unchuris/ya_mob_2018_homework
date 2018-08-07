package com.kissedcode.finance.model.worker

import androidx.work.Worker
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.database.AppDatabase
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.DeferTransaction
import com.kissedcode.finance.model.entity.IdleDeferTransaction
import com.kissedcode.finance.model.entity.MyTransaction
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.utils.convert
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar

class PeriodicTransactionWorker : Worker() {

    companion object {
        internal val TAG = "PeriodicOperationsWorker"
    }

    lateinit var db: AppDatabase
    private val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private val month = Calendar.getInstance().get(Calendar.MONTH)
    private val year = Calendar.getInstance().get(Calendar.YEAR)

    override fun doWork(): Result {
        db = AppDatabase.getInstance(applicationContext)
        Observable.fromCallable { db.getDeferTransactionDao().getByDate(day, month, year) }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    result -> deferTransactionSuccess(result)
                }

        return Result.SUCCESS
    }

    private fun deferTransactionSuccess(transactions: List<DeferTransaction>) {
        Observable.fromCallable { db.currencyDao().all }
                .subscribeOn(Schedulers.io())
                .subscribe { result ->
                    currencySuccess(result, transactions)
                }
    }

    private fun currencySuccess(currency: List<Currency>, transactions: List<DeferTransaction>) {
        for (transaction in transactions) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            calendar.add(Calendar.DAY_OF_MONTH, transaction.repeatDays)

            val idleDeferTransaction = IdleDeferTransaction(
                null,
                    idleDeferTransactionDate = transaction.deferTransactionDate,
                    idleDeferTransactionAmount = transaction.deferTransactionAmount,
                            categoryID = transaction.category.categoryId!!,
                            currencyID = transaction.currency.currencyId!!,
                            walletID = transaction.wallet.walletId!!,
                            nextRepeatDay = calendar.get(Calendar.DAY_OF_MONTH),
                            nextRepeatMonth = calendar.get(Calendar.MONTH),
                            nextRepeatYear = calendar.get(Calendar.YEAR),
                            repeatDays = transaction.repeatDays
            )

            db.getDeferTransactionDao().insert(idleDeferTransaction)

            newRequest(transaction, currency)
        }
    }

    private fun newRequest(t: DeferTransaction, currency: List<Currency>) {
        val transaction = MyTransaction(null,
                myTransactionDate = Calendar.getInstance().time,
                myTransactionAmount = t.deferTransactionAmount,
                categoryID = t.category.categoryId!!,
                currencyID = t.currency.currencyId!!,
                walletID = t.wallet.walletId!!)

        addTransaction(transaction, t.wallet, currency, t.category.categoryType)
    }

    private fun addTransaction(transaction: MyTransaction, wallet: Wallet, currency: List<Currency>, type: OperationType) {
        val transactionCurrency = currency.find { t -> t.currencyId == transaction.currencyID }
        val walletCurrency = currency.find { t -> t.currencyId == wallet.currencyID }
        val transactionAmount = if (type == OperationType.SPEND) -transaction.myTransactionAmount else transaction.myTransactionAmount
        val amount = if (transactionCurrency == walletCurrency) transactionAmount
        else convert(transactionAmount, transactionCurrency!!, walletCurrency!!)
        db.walletTransactionDao().insertTransactionAndUpdateWallet(transaction, amount)
    }
}
