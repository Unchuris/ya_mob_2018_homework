package com.kissedcode.finance.accounts

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CbrApi
import com.kissedcode.finance.model.IdleWalletDao
import com.kissedcode.finance.model.dto.CbrResponse
import com.kissedcode.finance.model.entity.IdleWallet
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.repository.Rate
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountsViewModel(private val walletDao: IdleWalletDao) : BaseViewModel() {

    private lateinit var wallets: List<IdleWallet>

    private var disposables = CompositeDisposable()

    @Inject
    lateinit var cbrApi: CbrApi

    var accounts: MutableLiveData<List<IdleWallet>> = MutableLiveData()
        private set

    init {
        initRate()
        disposables.add( walletDao.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        result -> onRetrievePostListSuccess(result)
                    })
    }

    private fun onRetrievePostListSuccess(postList: List<IdleWallet>) {
        wallets = postList
        accounts.value = wallets
    }

    private fun initRate() {
        disposables.add(cbrApi.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnTerminate { }
                .subscribe(
                        { result -> rateSuccess(result) },
                        { error() }
                ))
    }

    private fun rateSuccess(result: CbrResponse) {
        Rate.rate = result.valute!!.usd!!.value!!
    }

    private fun error() {
        Rate.rate = 63.34
    }

    fun deleteWallet(id: Int) {
        disposables.add(Completable.fromAction { walletDao.delete(getWallet(wallets[id])) }
                .subscribeOn(Schedulers.io())
                .subscribe {})
    }

    fun updateWallet(id: Int, name: String) {
        disposables.add(Completable.fromAction {
            wallets[id].walletName = name
            walletDao.update(getWallet(wallets[id])) }
                .subscribeOn(Schedulers.io())
                .subscribe {})
    }

    private fun getWallet(idl: IdleWallet): Wallet = Wallet(
                walletId = idl.IdleWalletId,
                walletName = idl.walletName,
                walletValue = idl.walletValue,
                currencyID = idl.currency.currencyId!!)

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
