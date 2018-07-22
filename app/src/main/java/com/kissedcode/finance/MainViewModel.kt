package com.kissedcode.finance

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val balance = MutableLiveData<Double>()

    init {
        // default livedata values
        balance.value = 1000.5
    }
}