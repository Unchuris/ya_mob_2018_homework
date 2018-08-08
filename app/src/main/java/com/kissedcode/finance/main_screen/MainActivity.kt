package com.kissedcode.finance.main_screen

import android.support.v4.app.Fragment
import com.kissedcode.finance.R
import com.kissedcode.finance.accounts.AccountsFragment
import com.kissedcode.finance.settings.SettingsFragment
import com.kissedcode.finance.statistics.StatisticsFragment
import com.kissedcode.finance.templates.TemplatesFragment
import com.kissedcode.finance.transaction.TransactionFragment

class MainActivity : DrawerActivity() {


    override fun getDrawerHeaderRes() = R.layout.drawer_header

    override fun getDrawerMenuRes() = R.menu.drawer

    override fun getInitialScreenId() = R.id.menuitem_drawer_accounts

    override fun getScreenFragment(drawerItemId: Int): Fragment {

        return when (drawerItemId) {
            R.id.menuitem_drawer_accounts -> if (resources.getBoolean(R.bool.is_tablet))
                AccountsFragment.newInstance() else FragmentMainScreen.newInstance()
            R.id.menuitem_drawer_statistics -> StatisticsFragment()
            R.id.menuitem_drawer_settings -> SettingsFragment()
            R.id.menuitem_drawer_templates -> TemplatesFragment.newInstance()
            else -> TransactionFragment.newInstance()
        }
    }
}
