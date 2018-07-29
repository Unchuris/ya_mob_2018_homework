package com.kissedcode.finance.activity

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kissedcode.finance.model.Currency

class MainViewModel : ViewModel() {

    val exchange = 60.5

    val balance = MediatorLiveData<Double>()

    val balanceUsd = MutableLiveData<Double>()

    val currencies = MutableLiveData<Array<Currency>>()

    val currency = MutableLiveData<Currency>()


    init {

        // default livedata values
        balance.addSource(balanceUsd) {
            val exchange: Double = when (currency.value) {
                Currency.USD -> 1.0
                Currency.RUB -> this.exchange
                else -> 0.0
            }

            balance.value = exchange * (balanceUsd.value?: 0.0)
        }

        balance.addSource(currency) {
            val exchange: Double = when (currency.value) {
                Currency.USD -> 1.0
                Currency.RUB -> this.exchange
                else -> 0.0
            }

            balance.value = exchange * (balanceUsd.value?: 0.0)
        }

        balanceUsd.value = 100.5

        currencies.value = arrayOf(Currency.USD, Currency.RUB)

        currency.value = Currency.RUB
    }

    // controller //////////////////////////////////////////////////////////////////////////////

    fun onCurrencySelected(index: Int) {

        currency.value = currencies.value?.get(index)
    }
}