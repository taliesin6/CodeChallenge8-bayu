package com.example.codechallenge8.ui.landingPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codechallenge8.R
import com.example.codechallenge8.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        val position = 0
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        dots_indicator.setViewPager(viewPager)
    }
}