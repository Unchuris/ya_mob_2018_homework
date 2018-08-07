package com.kissedcode.finance.settings

import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity

class SettingsFragment : DrawerFragment() {

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId)
    }

    // required overrides //////////////////////////////////////////////////////////////////////

    override fun getLayoutRes() = R.layout.fragment_settings

    override fun getTitleRes() = R.string.screen_title_settings
}
