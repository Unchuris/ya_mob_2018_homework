package com.kissedcode.finance.main_screen

import android.support.v4.app.Fragment
import com.kissedcode.finance.R
import com.kissedcode.finance.about.AboutFragment
import com.kissedcode.finance.accounts.AccountsFragment
import com.kissedcode.finance.main_screen.Screens.ABOUT_SCREEN
import com.kissedcode.finance.main_screen.Screens.ACCOUNT_SCREEN
import com.kissedcode.finance.main_screen.Screens.OPERATION_FRAGMENT_SCREEN
import com.kissedcode.finance.main_screen.Screens.STATISTICS_SCREEN
import com.kissedcode.finance.main_screen.Screens.TEMPLATES_SCREEN
import com.kissedcode.finance.statistics.StatisticsFragment
import com.kissedcode.finance.templates.TemplatesFragment
import com.kissedcode.finance.transaction.TransactionFragment

class MainActivity : DrawerActivity() {

    override fun getDrawerHeaderRes() = R.layout.drawer_header

    override fun getDrawerMenuRes() = R.menu.drawer

    override fun getInitialScreenId() = R.id.menuitem_drawer_accounts

    override fun getScreenFragment(screen: String): Fragment {

        return when (screen) {
            ACCOUNT_SCREEN -> if (resources.getBoolean(R.bool.is_tablet))
                AccountsFragment.newInstance() else FragmentMainScreen.newInstance()
            OPERATION_FRAGMENT_SCREEN -> TransactionFragment.newInstance()
            STATISTICS_SCREEN -> StatisticsFragment.newInstance()
            TEMPLATES_SCREEN -> TemplatesFragment.newInstance()
            ABOUT_SCREEN -> AboutFragment.newInstance()
            else -> AccountsFragment.newInstance()
        }
    }

    override fun getScreenName(drawerItemId: Int): String {

        return when (drawerItemId) {
            R.id.menuitem_drawer_accounts -> ACCOUNT_SCREEN
            R.id.menuitem_drawer_statistics -> STATISTICS_SCREEN
            R.id.menuitem_drawer_templates -> TEMPLATES_SCREEN
            R.id.menuitem_drawer_about -> ABOUT_SCREEN
            else -> ""
        }
    }
}
