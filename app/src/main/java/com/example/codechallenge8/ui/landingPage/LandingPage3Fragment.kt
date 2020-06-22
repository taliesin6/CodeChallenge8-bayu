package com.example.codechallenge8.ui.landingPage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_landing_page3.*

class LandingPage3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing_page3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(activity?.baseContext!!)
            .load("https://raw.githubusercontent.com/ariesmo/Code_Challenge5/master/app/src/main/res/drawable/men.png")
            .apply(RequestOptions().override(350, 550))
            .into(imageLandingPage3)

        btn_next.setOnClickListener {
            Intent(activity, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}