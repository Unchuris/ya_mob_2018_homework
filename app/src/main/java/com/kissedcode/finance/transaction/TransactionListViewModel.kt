package com.kissedcode.finance.transaction

import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.IdleTransactionDao
import com.kissedcode.finance.model.entity.IdleTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TransactionListViewModel(idleTransactionDao: IdleTransactionDao) : BaseViewModel() {

    private var subscription: Disposable

    val transactionListAdapter: TransactionListAdapter = TransactionListAdapter()

    init {
        subscription = idleTransactionDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> onRetrievePostListSuccess(result) }
    }

    private fun onRetrievePostListSuccess(postList: List<IdleTransaction>) {
        transactionListAdapter.updatePostList(postList as MutableList<IdleTransaction>)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
