package com.kissedcode.finance.model.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.arch.persistence.room.Transaction
import android.content.Context
import com.kissedcode.finance.model.*
import java.util.concurrent.Executors

@Database(entities = [(Wallet::class), (Currency::class), (Category::class), (Transaction::class)], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: getDataBase(context).also { INSTANCE = it }
                }

        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tracker.db")
                        .allowMainThreadQueries()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadScheduledExecutor().execute {
                                    getInstance(context).currencyDao().insertAll(initCurrency())
                                    getInstance(context).walletDao().insertAll(initWallet())
                                }
                            }
                        })
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
