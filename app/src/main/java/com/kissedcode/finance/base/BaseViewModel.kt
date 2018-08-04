package com.kissedcode.finance.base

import android.arch.lifecycle.ViewModel
import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.accounts.operation.CurrencyViewModel
import com.kissedcode.finance.accounts.operation.OperationViewModel
import com.kissedcode.finance.accounts.operation.WalletTransactionViewModel
import com.kissedcode.finance.injection.component.DaggerViewModelInjector
import com.kissedcode.finance.injection.component.ViewModelInjector
import com.kissedcode.finance.injection.module.NetworkModule
import com.kissedcode.finance.transaction.TransactionListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is AccountsViewModel -> injector.inject(this)
            is OperationViewModel -> injector.inject(this)
            is CurrencyViewModel -> injector.inject(this)
            is WalletTransactionViewModel -> injector.inject(this)
            is TransactionListViewModel -> injector.inject(this)
        }
    }
}
