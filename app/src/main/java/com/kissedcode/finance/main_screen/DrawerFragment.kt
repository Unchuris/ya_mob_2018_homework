package com.kissedcode.finance.main_screen

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class DrawerFragment : Fragment() {

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(getTitleRes())
    }

    // abstract ////////////////////////////////////////////////////////////////////////////////

    @LayoutRes
    abstract fun getLayoutRes(): Int

    @StringRes
    abstract fun getTitleRes(): Int
}