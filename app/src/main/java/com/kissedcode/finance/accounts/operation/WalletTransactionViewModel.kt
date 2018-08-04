package com.kissedcode.finance.accounts.operation

import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.WalletTransactionDao
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.MyTransaction
import com.kissedcode.finance.model.entity.Wallet
import com.kissedcode.finance.utils.convert

class WalletTransactionViewModel(private val walletTransactionDao: WalletTransactionDao) : BaseViewModel() {

    fun addTransaction(transaction: MyTransaction, wallet: Wallet, currency: List<Currency>, type: OperationType) {
        val transactionCurrency = currency.find { t -> t.currencyId == transaction.currencyID }
        val walletCurrency = currency.find { t -> t.currencyId == wallet.currencyID }
        val transactionAmount = if (type == OperationType.SPEND) -transaction.myTransactionAmount else transaction.myTransactionAmount
        val amount = if (transactionCurrency == walletCurrency) transactionAmount
                else convert(transactionAmount, transactionCurrency!!, walletCurrency!!)
        walletTransactionDao.insertTransactionAndUpdateWallet(transaction, transaction.walletID, amount)
    }

}