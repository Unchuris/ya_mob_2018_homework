package com.kissedcode.finance.statistics

import android.arch.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieEntry
import com.kissedcode.finance.base.BaseViewModel
import com.kissedcode.finance.model.CategoryDao
import com.kissedcode.finance.model.IdleTransactionDao
import com.kissedcode.finance.model.IdleWalletDao
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.IdleTransaction
import com.kissedcode.finance.model.entity.IdleWallet
import com.kissedcode.finance.utils.convert
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Date

class StatisticsViewModel(private val idleTransactionDao: IdleTransactionDao,
                          private val categoryDao: CategoryDao,
                          private val walletDao: IdleWalletDao) : BaseViewModel() {

    private var disposables = CompositeDisposable()

    val chartData: MutableLiveData<PieData> = MutableLiveData()

    var category: List<Category> = emptyList()

    var wallets: List<IdleWallet> = emptyList()

    init {
        initCategory()
        initWallet()
    }

    private fun initCategory() {
        disposables.add(Observable.fromCallable { categoryDao.all }
                .concatMap { dbPostList -> Observable.just(dbPostList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> categoryDaoSuccess(result) })
    }

    private fun initWallet() {
        disposables.add( walletDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    result -> walletSuccess(result)
                })
    }

    private fun walletSuccess(postList: List<IdleWallet>) {
        wallets = postList
    }

    private fun categoryDaoSuccess(postList: List<Category>) {
        category = postList
    }

    fun createDiagram(dateFrom: Date, dateTo: Date, type: OperationType) {
        disposables.add(idleTransactionDao.getFilterByDateInterval(dateFrom, dateTo, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> onRetrievePostListSuccess(result) })
    }

    private fun onRetrievePostListSuccess(postList: List<IdleTransaction>) {
        chartData.value = getPieData(postList)
    }

    private fun getPieData(postList: List<IdleTransaction>): PieData {
        var sum = 0f
        val yEntrys = ArrayList<PieEntry>()
        category.forEach() { category ->
            postList.filter { el ->
                (el.category.categoryName == category.categoryName) }.forEach { t ->
                val wallet = getWallet(t.wallet.walletId!!)
                sum += if (t.currency.standardName != wallet!!.currency.standardName) {
                    convert(t.idleTransactionAmount, t.currency, wallet.currency).toFloat()
                } else t.idleTransactionAmount.toFloat()
            }

            if (sum > 0) {
                yEntrys.add(PieEntry(sum, category.categoryName))
            }

            sum = 0f
        }

        return getChart(yEntrys)
    }

    fun getWallet(id: Int) = wallets.find { it -> it.IdleWalletId == id }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}