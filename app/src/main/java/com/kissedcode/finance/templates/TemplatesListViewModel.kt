package com.kissedcode.finance.templates

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.IdleTransactionDao
import com.kissedcode.finance.model.TransactionDao
import com.kissedcode.finance.model.WalletTransactionDao
import com.kissedcode.finance.model.entity.IdleTransaction
import com.kissedcode.finance.model.entity.MyTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar

class TemplatesListViewModel(idleTransactionDao: IdleTransactionDao,
                             private val walletTransactionDao: WalletTransactionDao,
                             private val transactionDao: TransactionDao)
    : BaseViewModel(), TemplatesListAdapter.RecycleOnClickListenerCallback {

    val templatesListAdapter: TemplatesListAdapter = TemplatesListAdapter(this)

    var open: MutableLiveData<IdleTransaction> = MutableLiveData()
        private set

    private var subscription: Disposable

    init {
        subscription = idleTransactionDao.getAllTemplates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> onRetrievePostListSuccess(result) }
    }

    private fun onRetrievePostListSuccess(postList: List<IdleTransaction>) {
        templatesListAdapter.updatePostList(postList as MutableList<IdleTransaction>)
    }

    override fun onTemplateDelete(transaction: IdleTransaction) {
        transactionDao.deleteById(transaction.IdleTransactionId!!)
    }

    override fun onApplyTemplate(transaction: IdleTransaction) {
        val t = MyTransaction(null,
                myTransactionDate = Calendar.getInstance().time,
                myTransactionAmount = transaction.idleTransactionAmount,
                categoryID = transaction.category.categoryId!!,
                currencyID = transaction.currency.currencyId!!,
                walletID = transaction.wallet.walletId!!,
                template = false)
        walletTransactionDao.insertTransactionAndUpdateWallet(t, t.myTransactionAmount)
    }

    override fun openFragment(transaction: IdleTransaction) {
        open.value = transaction
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}