package com.kissedcode.finance.main_screen

import android.support.v4.app.FragmentPagerAdapter
import com.kissedcode.finance.R
import android.content.Context;
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.kissedcode.finance.accounts.AccountsFragment
import com.kissedcode.finance.transaction.TransactionFragment

class SampleFragmentPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private val PAGE_COUNT = 2

    private val tabTitles: Array<String> = arrayOf(context.getString(R.string.main_screen_tab_main), context.getString(R.string.main_screen_tab_operations))

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return AccountsFragment.newInstance()
            1 -> return TransactionFragment.newInstance()
        }
        return null
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}