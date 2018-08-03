package com.kissedcode.finance.accounts

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.model.WalletDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AccountsViewModel(private val walletDao: WalletDao) : BaseViewModel() {

    private var subscription: Disposable

    var accounts: MutableLiveData<List<Wallet>> = MutableLiveData()
        private set

    init {
        subscription = Observable.fromCallable { walletDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> onRetrievePostListSuccess(result) }
    }

    private fun onRetrievePostListSuccess(postList:List<Wallet>){
        accounts.value = postList
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
