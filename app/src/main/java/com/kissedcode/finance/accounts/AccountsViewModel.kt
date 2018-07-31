package com.kissedcode.finance.accounts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kissedcode.finance.R

class AccountsViewModel : ViewModel() {

    var accounts: LiveData<List<Account>> = MutableLiveData()
        private set

    init {
        (accounts as MutableLiveData).value = listOf(
                //Account("Cash"),
                Account("Cash", R.drawable.cash),
                //Account("Credit card"))
                Account("Credit card", R.drawable.credit_card))
    }


}