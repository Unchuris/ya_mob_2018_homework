package com.kissedcode.finance.main_screen

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class DrawerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpToolbarTitle(getTitleRes())
    }

    abstract fun setUpToolbarTitle(resId: Int)

    @LayoutRes
    abstract fun getLayoutRes(): Int

    @StringRes
    abstract fun getTitleRes(): Int

    protected fun replace(container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
                .replace(container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

}
