package com.kissedcode.finance.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.accounts.operation.CurrencyViewModel
import com.kissedcode.finance.accounts.operation.OperationViewModel
import com.kissedcode.finance.accounts.operation.WalletTransactionViewModel
import com.kissedcode.finance.model.database.AppDatabase.Companion.getInstance
import com.kissedcode.finance.transaction.TransactionListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            return AccountsViewModel(getInstance(activity).idleWalletDao()) as T
        }
        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            return OperationViewModel(getInstance(activity).categoryDao()) as T
        }
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(getInstance(activity).currencyDao()) as T
        }
        if (modelClass.isAssignableFrom(WalletTransactionViewModel::class.java)) {
            return WalletTransactionViewModel(getInstance(activity).walletTransactionDao()) as T
        }
        if (modelClass.isAssignableFrom(TransactionListViewModel::class.java)) {
            return TransactionListViewModel(getInstance(activity).getIdleTransactionDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
