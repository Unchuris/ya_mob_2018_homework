package com.kissedcode.finance.accounts

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CurrencyDao
import com.kissedcode.finance.model.WalletDao
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.Wallet
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddAccountViewModel(
    private val currencyDao: CurrencyDao,
    private val walletDao: WalletDao
) : BaseViewModel() {
    private var subscription: Disposable

    var currency: MutableLiveData<List<Currency>> = MutableLiveData()
        private set

    init {
        subscription = (Observable.fromCallable { currencyDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> currencyDaoSuccess(result) })
    }

    private fun currencyDaoSuccess(postList: List<Currency>) {
        currency.value = postList
    }

    fun insert(wallet: Wallet) {
        Completable.fromAction { walletDao.insertAll(wallet) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}