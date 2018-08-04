package com.kissedcode.finance.accounts

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CbrApi
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.model.WalletDao
import com.kissedcode.finance.model.dto.CbrResponse
import com.kissedcode.finance.model.dto.Valute
import com.kissedcode.finance.repository.Rate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountsViewModel(private val walletDao: WalletDao) : BaseViewModel() {

    private var subscription: Disposable

    private lateinit var subscriptionRate: Disposable

    @Inject
    lateinit var cbrApi: CbrApi

    var accounts: MutableLiveData<List<Wallet>> = MutableLiveData()
        private set

    init {
        initRate()
        subscription = walletDao.all
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        result -> onRetrievePostListSuccess(result)
                    }

    }

    private fun onRetrievePostListSuccess(postList:List<Wallet>){
        accounts.value = postList
    }

    private fun initRate() {
        subscriptionRate = cbrApi.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnTerminate { }
                .subscribe(
                        { result -> rateSuccess(result) },
                        { error()}
                )
    }

    private fun rateSuccess(result: CbrResponse) {
        Rate.rate = result.valute!!.usd!!.value!!
    }

    private fun error() {
        Rate.rate = 63.34
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        subscriptionRate.dispose()
    }
}
