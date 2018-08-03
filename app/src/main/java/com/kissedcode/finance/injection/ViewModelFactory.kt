package com.kissedcode.finance.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.accounts.operation.CurrencyViewModel
import com.kissedcode.finance.accounts.operation.OperationViewModel
import com.kissedcode.finance.model.database.AppDatabase.Companion.getInstance

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            val db = getInstance(activity)
            @Suppress("UNCHECKED_CAST")
            return AccountsViewModel(db.walletDao()) as T
        }
        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            val db = getInstance(activity)
            @Suppress("UNCHECKED_CAST")
            return OperationViewModel(db.categoryDao()) as T
        }
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            val db = getInstance(activity)
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(db.currencyDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
