package com.kissedcode.finance.templates

import android.arch.lifecycle.MutableLiveData
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.IdleTransaction

class TemplatesViewModel : BaseViewModel() {

    val categoryName = MutableLiveData<String>()
    val amount = MutableLiveData<String>()
    val income = MutableLiveData<Boolean>()

    fun bind(post: IdleTransaction) {
        categoryName.value = post.category.categoryName
        amount.value = String.format("%.2f", post.idleTransactionAmount) + " " + post.currency.symbol
        income.value = when (post.category.categoryType) {
            OperationType.INCOME -> false
            OperationType.SPEND -> true
        }
    }

}
