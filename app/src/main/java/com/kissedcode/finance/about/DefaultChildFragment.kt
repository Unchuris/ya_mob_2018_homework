package com.kissedcode.finance.about

import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity

class DefaultChildFragment : DrawerFragment() {

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId, true)
    }

    override fun getLayoutRes() = R.layout.image_fragment

    override fun getTitleRes() = R.string.screen_title_settings

    companion object {
        fun newInstance(): DefaultChildFragment {
            return DefaultChildFragment()
        }
    }
}
