package com.kissedcode.finance.accounts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kissedcode.finance.model.Wallet

class AccountsViewModel : ViewModel() {

    var accounts: LiveData<List<Wallet>> = MutableLiveData()
        private set

    init {
        (accounts as MutableLiveData).value = emptyList()
    }
}
