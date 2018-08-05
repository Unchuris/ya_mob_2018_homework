package com.kissedcode.finance.model.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.content.Context
import com.kissedcode.finance.model.*
import com.kissedcode.finance.model.Converters
import com.kissedcode.finance.model.entity.*
import java.util.concurrent.Executors

@Database(entities = [Wallet::class, Currency::class, Category::class, MyTransaction::class, IdleDeferTransaction::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    abstract fun idleWalletDao(): IdleWalletDao

    abstract fun currencyDao(): CurrencyDao

    abstract fun categoryDao(): CategoryDao

    abstract fun getIdleTransactionDao(): IdleTransactionDao

    abstract fun walletTransactionDao(): WalletTransactionDao

    abstract fun getDeferTransactionDao(): DeferTransactionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: getDataBase(context).also { INSTANCE = it }
                }

        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tracker")
                        .allowMainThreadQueries()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadScheduledExecutor().execute {
                                    getInstance(context).currencyDao().insertAll(*initCurrency().toTypedArray())
                                    getInstance(context).walletDao().insertAll(*initWallet().toTypedArray())
                                    getInstance(context).categoryDao().insertAll(*initCategory().toTypedArray())
                                }
                            }
                        })
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
