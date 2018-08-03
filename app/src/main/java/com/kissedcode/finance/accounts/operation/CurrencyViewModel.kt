package com.kissedcode.finance.accounts.operation

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CurrencyDao
import com.kissedcode.finance.model.entity.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(private val currencyDao: CurrencyDao) : BaseViewModel() {

    private var subscriptionCurrency: Disposable

    var currency: MutableLiveData<List<Currency>> = MutableLiveData()
        private set

    init {
        subscriptionCurrency = Observable.fromCallable { currencyDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> currencyDaoSuccess(result) }
    }

    private fun currencyDaoSuccess(postList:List<Currency>){
        currency.value = postList
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionCurrency.dispose()
    }
}