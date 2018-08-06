package com.kissedcode.finance.main_screen

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kissedcode.finance.R

class FragmentMainScreen : Fragment() {

    private lateinit var adapter: SampleFragmentPagerAdapter

    companion object {
        fun newInstance(): FragmentMainScreen {
            return FragmentMainScreen()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_main_screen, container, false)
        adapter = SampleFragmentPagerAdapter(childFragmentManager, context!!)
        val viewPager = v.findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = v.findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        return v
    }
}
