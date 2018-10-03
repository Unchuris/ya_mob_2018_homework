package com.kissedcode.finance.about

import android.os.Bundle
import android.view.View
import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity

class AboutFragment : DrawerFragment() {

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_about

    override fun getTitleRes(): Int = R.string.activity_about

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (resources.getBoolean(R.bool.is_tablet)) {
            replace(R.id.fragmentContainer_child, DefaultChildFragment.newInstance(), false)
        }
    }

}
