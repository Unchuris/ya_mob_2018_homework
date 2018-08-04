package com.kissedcode.finance.accounts.operation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CategoryDao
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OperationViewModel(private val categoryDao: CategoryDao) : BaseViewModel() {

    private var subscriptionCategory: Disposable

    var category: List<Category> = emptyList()

    var type = MutableLiveData<OperationType>()
        set(value) {
            type.value = value.value
        }

    val categories = Transformations.switchMap(type) { t ->
        filterByType(t)
    }?: getAll()

    init {
        subscriptionCategory = Observable.fromCallable { categoryDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> categoryDaoSuccess(result) }
    }

    private fun filterByType(type : OperationType) : LiveData<List<Category>> {
        val c = MutableLiveData<List<Category>>()
        c.value = category.filter { f -> f.categoryType == type }
        return c
    }

    private fun getAll() : LiveData<List<Category>> {
        val c = MutableLiveData<List<Category>>()
        c.value = category
        return c
    }

    private fun categoryDaoSuccess(postList:List<Category>) {
        val c = MutableLiveData<List<Category>>()
        c.value = postList
        category = postList
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionCategory.dispose()
    }
}