package com.kissedcode.finance.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import com.kissedcode.finance.accounts.AccountsViewModel
import com.kissedcode.finance.model.database.AppDatabase
import com.kissedcode.finance.model.database.AppDatabase.Companion.getDataBase

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            val db = getDataBase(activity)
            //Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "tracker").build()
            @Suppress("UNCHECKED_CAST")
            return AccountsViewModel(db.walletDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
