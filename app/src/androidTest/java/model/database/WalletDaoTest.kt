package model.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.kissedcode.finance.model.WalletDao
import com.kissedcode.finance.model.database.AppDatabase
import com.kissedcode.finance.model.database.initCategory
import com.kissedcode.finance.model.database.initCurrency
import com.kissedcode.finance.model.database.initWallet
import com.kissedcode.finance.model.entity.Wallet
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class WalletDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var walletDao: WalletDao

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java).build()
        walletDao = db.walletDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun initWalletInDB() {
        val wallet = arrayListOf(
                Wallet(1, "Card", 00.00, 2),
                Wallet(2, "Credit card", 00.00, 1),
                Wallet(3, "Debit card", 00.00, 2))
        db.currencyDao().insertAll(*initCurrency().toTypedArray())
        walletDao.insertAll(*initWallet().toTypedArray())

        walletDao.all
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 3 && wallet[0].copy(walletId = 1) == it[0] &&
                            wallet[1].copy(walletId = 2) == it[1] &&
                            wallet[2].copy(walletId = 3) == it[2]
                }
    }

    @Test
    fun emptyDb_getAll_returnsEmpty() {
        walletDao.all
                .subscribeOn(Schedulers.newThread())
                .test()
                .assertEmpty()
    }

}
