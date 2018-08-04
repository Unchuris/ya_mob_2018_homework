package com.kissedcode.finance.accounts.operation

import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.WalletTransactionDao
import com.kissedcode.finance.model.entity.MyTransaction
import com.kissedcode.finance.model.entity.Wallet

class WalletTransactionViewModel(private val walletTransactionDao: WalletTransactionDao) : BaseViewModel() {

    fun addTransaction(transaction: MyTransaction, wallet: Wallet) {
        val amount = transaction.amount + wallet.value

        walletTransactionDao.insertTransactionAndUpdateWallet(transaction, transaction.walletID, amount)
    }

}