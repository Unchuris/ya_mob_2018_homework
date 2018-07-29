package com.kissedcode.finance.main_screen

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class DrawerFragment : Fragment() {

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(getTitleRes())
    }

    // abstract ////////////////////////////////////////////////////////////////////////////////

    @StringRes
    abstract fun getTitleRes(): Int
}