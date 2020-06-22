package com.example.codechallenge8.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.codechallenge8.ui.landingPage.LandingPage1Fragment
import com.example.codechallenge8.ui.landingPage.LandingPage2Fragment
import com.example.codechallenge8.ui.landingPage.LandingPage3Fragment

class ViewPagerAdapter(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = arrayOf(
        LandingPage1Fragment(),
        LandingPage2Fragment(),
        LandingPage3Fragment()
    )

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}