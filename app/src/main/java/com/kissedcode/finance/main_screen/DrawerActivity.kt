package com.kissedcode.finance.main_screen

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.facebook.stetho.Stetho
import com.kissedcode.finance.R
import com.kissedcode.finance.about.AboutActivity
import com.kissedcode.finance.model.worker.PeriodicTransactionWorker
import kotlinx.android.synthetic.main.activity_drawer.*
import java.util.concurrent.TimeUnit

abstract class DrawerActivity : AppCompatActivity() {

    val STATE_SCREEN_ID = "screen_id"

    var screenId = 0

    // menu ////////////////////////////////////////////////////////////////////////////////////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        setContentView(R.layout.activity_drawer)
        // restore state
        savedInstanceState?.apply {
            screenId = savedInstanceState[STATE_SCREEN_ID] as Int? ?: 0
        }

        // ui
        setupUi()

        // initial fragment
        if (screenId == 0)
            screenId = getInitialScreenId()

        changeScreenFragment(getScreenFragment(screenId))

        initPeriodicTransactionManager()

    }

    private fun initPeriodicTransactionManager() {
        WorkManager.getInstance().getStatusesByTag(PeriodicTransactionWorker.TAG).observeForever {
            for (work in it!!) {
                if (!work.state.isFinished) {
                    return@observeForever
                }
            }

            val periodicWorkRequest = PeriodicWorkRequest
                    .Builder(PeriodicTransactionWorker::class.java, 6, TimeUnit.HOURS)
                    .addTag(PeriodicTransactionWorker.TAG)
                    .build()

            Log.i(::MainActivity.name, "Periodic transaction work manager start")
            WorkManager.getInstance().enqueue(periodicWorkRequest)

        }
    }

    private fun setupUi() {

        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.burger_menu)
        }

        // navigation drawer
        navigationView.inflateHeaderView(getDrawerHeaderRes())
        navigationView.inflateMenu(getDrawerMenuRes())
        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            screenId = it.itemId

            if (screenId == R.id.menuitem_drawer_about) {
                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)
            }
            else {
                val fragment = getScreenFragment(it.itemId)
                changeScreenFragment(fragment)
            }

            true
        }
    }

    private fun changeScreenFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATE_SCREEN_ID, screenId)
    }

    // abstract ////////////////////////////////////////////////////////////////////////////////

    @LayoutRes
    abstract fun getDrawerHeaderRes(): Int

    @MenuRes
    abstract fun getDrawerMenuRes(): Int

    @IdRes
    abstract fun getInitialScreenId() : Int

    abstract fun getScreenFragment(@IdRes drawerItemId: Int): Fragment
}