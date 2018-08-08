package com.kissedcode.finance.main_screen

import android.content.Intent
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
import com.kissedcode.finance.about.AboutActivity
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

        openScreen(screenId, true)

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
            if (screenId == R.id.menuitem_drawer_about) {
                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)
            } else {
                openScreen(screenId, true)
            }
            true
        }
        initToggle()
    }

    private fun main(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(id, fragment)
                .commit()
    }

    fun openScreen(id: Int, isParent: Boolean) {
        val fragment: Fragment = getScreenFragment(id)
        val containerId: Int = if (isTablet) {
            if (isParent) R.id.fragmentContainer
            else R.id.fragmentContainer_child
        } else {
            if (isParent) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            R.id.fragmentContainer
        }
        if (id == R.id.menuitem_drawer_accounts || id == R.id.transition_current_scene) {
            main(containerId, fragment)
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(fragment.tag)
                    .commit()
        }
    }

    private fun changeScreenFragment(id: Int) {
        val containerId: Int = R.id.fragmentContainer
        val fragment = getScreenFragment(id)
        if (id == R.id.menuitem_drawer_accounts) {
            //main(1, fragment)
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATE_SCREEN_ID, screenId)
    }

    fun updateToolBar(titleResId: Int) {
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

    abstract fun getScreenFragment(@IdRes drawerItemId: Int): Fragment
}
