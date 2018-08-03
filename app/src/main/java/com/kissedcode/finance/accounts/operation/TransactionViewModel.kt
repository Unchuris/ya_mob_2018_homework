package com.kissedcode.finance.accounts.operation

import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.TransactionDao
import com.kissedcode.finance.model.entity.MyTransaction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TransactionViewModel(private val transactionDao: TransactionDao) : BaseViewModel() {

    private var subscription: Disposable

    init {
        subscription = Observable.fromCallable { transactionDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> transactionDaoSuccess(result) }
    }

    private fun transactionDaoSuccess(postList:List<MyTransaction>) {
        val a = postList
    }

    fun addTransaction(transaction: MyTransaction) {
        transactionDao.insertAll(transaction)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}