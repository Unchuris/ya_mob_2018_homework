package com.kissedcode.finance.statistics

import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity

class StatisticsFragment : DrawerFragment() {

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId)
    }

    override fun getLayoutRes() = R.layout.fragment_statistics

    override fun getTitleRes() = R.string.screen_title_statistics
}
