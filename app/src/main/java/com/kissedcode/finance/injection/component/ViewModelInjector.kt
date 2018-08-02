package com.kissedcode.finance.injection.component

import com.kissedcode.finance.accounts.AccountsViewModel
import dagger.Component
import com.kissedcode.finance.injection.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(accountsViewModel: AccountsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
