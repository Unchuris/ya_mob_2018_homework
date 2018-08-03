package com.kissedcode.finance.settings

import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment


class SettingsFragment : DrawerFragment() {

    // required overrides //////////////////////////////////////////////////////////////////////

    override fun getLayoutRes() = R.layout.fragment_settings

    override fun getTitleRes() = R.string.screen_title_settings
}