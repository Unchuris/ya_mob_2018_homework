package com.kissedcode.finance.main_screen

import android.support.v4.app.Fragment
import com.kissedcode.finance.R
import com.kissedcode.finance.settings.SettingsFragment
import com.kissedcode.finance.statistics.StatisticsFragment

class MainActivity : DrawerActivity() {

    override fun getDrawerHeaderRes() = R.layout.drawer_header

    override fun getDrawerMenuRes() = R.menu.drawer

    override fun getInitialScreenId() = R.id.menuitem_drawer_accounts

    override fun getScreenFragment(drawerItemId: Int): Fragment {

        return when (drawerItemId) {
            R.id.menuitem_drawer_accounts -> FragmentMainScreen.newInstance()
            R.id.menuitem_drawer_statistics -> StatisticsFragment()
            R.id.menuitem_drawer_settings -> SettingsFragment()
            else -> FragmentMainScreen.newInstance()
        }
    }
}
