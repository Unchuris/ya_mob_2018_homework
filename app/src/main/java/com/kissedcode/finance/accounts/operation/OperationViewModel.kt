package com.kissedcode.finance.accounts.operation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CategoryDao
import com.kissedcode.finance.model.DeferTransactionDao
import com.kissedcode.finance.model.CurrencyDao
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.TransactionDao
import com.kissedcode.finance.model.WalletTransactionDao
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.IdleWallet
import com.kissedcode.finance.model.entity.MyTransaction
import com.kissedcode.finance.model.entity.IdleDeferTransaction
import com.kissedcode.finance.utils.convert
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable

class OperationViewModel(
    private val categoryDao: CategoryDao,
    private val currencyDao: CurrencyDao,
    private val walletTransactionDao: WalletTransactionDao,
    private val transactionDao: TransactionDao,
    private val deferTransactionDao: DeferTransactionDao
) : BaseViewModel() {

    private var disposables = CompositeDisposable()

    var currency: MutableLiveData<List<Currency>> = MutableLiveData()
        private set

    var category: List<Category> = emptyList()

    var type = MutableLiveData<OperationType>()
        set(value) {
            type.value = value.value
        }

    val categories = Transformations.switchMap(type) { t ->
        filterByType(t)
    } ?: getAll()

    init {
        initCurrency()
        initCategory()
    }

    private fun initCategory() {
        disposables.add(Observable.fromCallable { categoryDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> categoryDaoSuccess(result) })
    }

    private fun initCurrency() {
        disposables.add(Observable.fromCallable { currencyDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> currencyDaoSuccess(result) })
    }

    private fun currencyDaoSuccess(postList: List<Currency>) {
        currency.value = postList
    }

    private fun filterByType(type: OperationType): LiveData<List<Category>> {
        val c = MutableLiveData<List<Category>>()
        c.value = category.filter { f -> f.categoryType == type }
        return c
    }

    private fun getAll(): LiveData<List<Category>> {
        val c = MutableLiveData<List<Category>>()
        c.value = category
        return c
    }

    private fun categoryDaoSuccess(postList: List<Category>) {
        val c = MutableLiveData<List<Category>>()
        c.value = postList
        category = postList
    }

    fun addToTemplate(transaction: MyTransaction) {
        disposables.add(Completable.fromAction { transactionDao.insertAll(transaction) }
                .subscribeOn(Schedulers.io())
                .subscribe {})
    }

    fun addTransaction(transaction: MyTransaction, wallet: IdleWallet, currency: List<Currency>, type: OperationType) {
        val transactionCurrency = currency.find { t -> t.currencyId == transaction.currencyID }
        val walletCurrency = wallet.currency
        val transactionAmount = if (type == OperationType.SPEND) -transaction.myTransactionAmount else transaction.myTransactionAmount
        val amount = if (transactionCurrency == walletCurrency) transactionAmount
        else convert(transactionAmount, transactionCurrency!!, walletCurrency)
        walletTransactionDao.insertTransactionAndUpdateWallet(transaction, amount)
    }

    fun getDeferTransactionDao(t: IdleDeferTransaction) {
        disposables.add(Completable.fromAction { deferTransactionDao.insert(t) }
                .subscribeOn(Schedulers.io())
                .subscribe {})
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
