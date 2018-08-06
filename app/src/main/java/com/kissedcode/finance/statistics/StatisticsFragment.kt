package com.kissedcode.finance.statistics

import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment

class StatisticsFragment : DrawerFragment() {

    // required overrides //////////////////////////////////////////////////////////////////////

    override fun getLayoutRes() = R.layout.fragment_statistics

    override fun getTitleRes() = R.string.screen_title_statistics
}
