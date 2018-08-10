package com.kissedcode.finance.main_screen

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.facebook.stetho.Stetho
import com.kissedcode.finance.R
import com.kissedcode.finance.accounts.AccountsFragment
import com.kissedcode.finance.main_screen.Screens.ACCOUNT_SCREEN
import com.kissedcode.finance.main_screen.Screens.OPERATION_FRAGMENT_SCREEN
import com.kissedcode.finance.main_screen.Screens.STATISTICS_SCREEN
import com.kissedcode.finance.model.worker.PeriodicTransactionWorker
import kotlinx.android.synthetic.main.activity_drawer.*
import java.util.concurrent.TimeUnit

abstract class DrawerActivity : AppCompatActivity() {

    private val STATE_SCREEN_ID = "screen_id"

    private var screenId = 0

    private var isTablet: Boolean = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isTablet = resources.getBoolean(R.bool.is_tablet)
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())

        setContentView(R.layout.activity_drawer)

        savedInstanceState?.apply {
            screenId = savedInstanceState[STATE_SCREEN_ID] as Int? ?: 0
        }

        setupUi()

        if (screenId == 0)
            screenId = getInitialScreenId()

        if (savedInstanceState == null) {
            openScreen(Screens.ACCOUNT_SCREEN, true)
        }

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

            WorkManager.getInstance().enqueue(periodicWorkRequest)
        }
    }

    private fun setupUi() {
        setSupportActionBar(toolbar)

        navigationView.inflateHeaderView(getDrawerHeaderRes())
        navigationView.inflateMenu(getDrawerMenuRes())
        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            screenId = it.itemId
            if (isTablet && screenId == R.id.menuitem_drawer_statistics) {
                openScreen(getScreenName(screenId), false)
            } else {
                openScreen(getScreenName(screenId), true)
            }
            true
        }
        initToggle()
    }

    fun openScreen(screen: String, isParent: Boolean) {
        val fragment: Fragment = getScreenFragment(screen)

        val containerId: Int = if (isTablet) {
            if (isParent) R.id.fragmentContainer else R.id.fragmentContainer_child
        } else {
            if (isParent) fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            R.id.fragmentContainer
        }
        if (screen == ACCOUNT_SCREEN || (screen == OPERATION_FRAGMENT_SCREEN && isTablet)
                || (screen == STATISTICS_SCREEN && isTablet) ) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(fragment.tag)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATE_SCREEN_ID, screenId)
    }

    fun updateToolBar(titleResId: Int, children: Boolean = false) {
        if (children) return
        toolbar.title = getString(titleResId)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        if (supportFragmentManager.backStackEntryCount == 0) {
            onShowMenuItem(R.id.graphics)
            initToggle()
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            onHideMenuItem(R.id.graphics)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            setBackArrow(true)
        }
    }

    fun setToolBar(titleResId: Int) {
        toolbar.title = getString(titleResId)
        toolbar.setNavigationOnClickListener { openScreen(ACCOUNT_SCREEN, true) }
        onHideMenuItem(R.id.graphics)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        setBackArrow(true)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun onShowMenuItem(resId: Int) {
        toolbar.menu.findItem(resId)?.isVisible = true
    }

    private fun onHideMenuItem(resId: Int) {
        toolbar.menu.findItem(resId)?.isVisible = false
    }

    private fun setBackArrow(state: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(state)
    }

    private fun initToggle() {
        setBackArrow(false)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @LayoutRes
    abstract fun getDrawerHeaderRes(): Int

    @MenuRes
    abstract fun getDrawerMenuRes(): Int

    @IdRes
    abstract fun getInitialScreenId(): Int

    abstract fun getScreenFragment(screen: String): Fragment

    abstract fun getScreenName(@IdRes drawerItemId: Int): String
}
