package com.kissedcode.finance.injection.component

import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.accounts.operation.CurrencyViewModel
import com.kissedcode.finance.accounts.operation.OperationViewModel
import com.kissedcode.finance.accounts.operation.WalletTransactionViewModel
import dagger.Component
import com.kissedcode.finance.injection.module.NetworkModule
import com.kissedcode.finance.transaction.TransactionListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(accountsViewModel: AccountsViewModel)
    fun inject(operationViewModel: OperationViewModel)
    fun inject(currencyViewModel: CurrencyViewModel)
    fun inject(transactionViewModel: WalletTransactionViewModel)
    fun inject(transactionListViewModel: TransactionListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
