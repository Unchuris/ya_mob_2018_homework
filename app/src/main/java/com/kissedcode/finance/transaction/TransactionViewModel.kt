package com.kissedcode.finance.transaction

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.R
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.IdleTransaction
import java.text.SimpleDateFormat

class TransactionViewModel : BaseViewModel() {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
    private val date = MutableLiveData<String>()
    private val amount = MutableLiveData<String>()
    private val operationType = MutableLiveData<Int>()
    private val currency = MutableLiveData<String>()
    private val imgCategory = MutableLiveData<String>()
    private val income = MutableLiveData<Boolean>()

    fun bind(post: IdleTransaction) {
        date.value = dateFormat.format(post.idleTransactionDate)
        amount.value = String.format("%.2f", post.idleTransactionAmount)
        operationType.value = when (post.category.categoryType) {
            OperationType.INCOME -> {
                income.value = false
                R.drawable.ic_arrow_upward_green_24dp
            }
            OperationType.SPEND -> {
                income.value = true
                R.drawable.ic_arrow_downward_red_24dp
            }
        }
        currency.value = post.currency.currencyName
        imgCategory.value = post.category.categoryName

    }

    fun getDate(): MutableLiveData<String> = date

    fun getAmount(): MutableLiveData<String> = amount

    fun getOperationType(): MutableLiveData<Int> = operationType

    fun getCurrency(): MutableLiveData<String> = currency

    fun getImageCategory(): MutableLiveData<String> = imgCategory

    fun getIncome(): MutableLiveData<Boolean> = income
}
