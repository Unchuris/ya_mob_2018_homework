package com.kissedcode.finance.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.accounts.AddAccountViewModel
import com.kissedcode.finance.accounts.operation.OperationViewModel
import com.kissedcode.finance.model.database.AppDatabase.Companion.getInstance
import com.kissedcode.finance.statistics.StatisticsViewModel
import com.kissedcode.finance.templates.TemplatesListViewModel
import com.kissedcode.finance.transaction.TransactionListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            return AccountsViewModel(getInstance(activity).idleWalletDao()) as T
        }
        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            val db = getInstance(activity)
            return OperationViewModel(
                    db.categoryDao(),
                    db.currencyDao(),
                    db.walletTransactionDao(),
                    db.transactionDao(),
                    db.getDeferTransactionDao()) as T
        }
        if (modelClass.isAssignableFrom(TransactionListViewModel::class.java)) {
            return TransactionListViewModel(getInstance(activity).getIdleTransactionDao()) as T
        }
        if (modelClass.isAssignableFrom(TemplatesListViewModel::class.java)) {
            val db = getInstance(activity)
            return TemplatesListViewModel(
                    db.getIdleTransactionDao(),
                    db.walletTransactionDao(),
                    db.transactionDao()) as T
        }
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            val db = getInstance(activity)
            return AddAccountViewModel(db.currencyDao(), db.walletDao()) as T
        }
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            val db = getInstance(activity)
            return StatisticsViewModel(
                    db.getIdleTransactionDao(),
                    db.categoryDao(),
                    db.idleWalletDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
