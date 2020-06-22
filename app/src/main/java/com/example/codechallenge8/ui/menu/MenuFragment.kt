package com.example.codechallenge8.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.game.CpuActivity
import com.example.codechallenge8.ui.game.PemainActivity
import com.example.codechallenge8.util.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() : MenuFragment {
            val menuFragment = MenuFragment()
            val args = Bundle()
            menuFragment.arguments = args
            return menuFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = activity?.let { SharedPrefManager.getInstance(it).user }
        val username = user?.username

        val animImage = AnimationUtils.loadAnimation(context, R.anim.imgbounce)
        val animTitle = AnimationUtils.loadAnimation(context, R.anim.bttwo)

        layout_pemain.setOnClickListener {
            Intent(context, PemainActivity::class.java).apply {
                startActivity(this)
            }
        }

        tv_menu.text = username.plus(" VS Pemain")

        btn_pemain.setOnClickListener {
            imageMenu.setImageResource(R.drawable.slide1)
            tv_menu.text = username.plus(" VS Pemain")

            //pass animation
            imageMenu.startAnimation(animImage)
            tv_menu.startAnimation(animTitle)

            //another animation
            btn_cpu.animate().scaleY(0.7F).scaleX(0.7F).setDuration(350).start()
            btn_pemain.animate().scaleY(1F).scaleX(1F).setDuration(350).start()
            dotmenu.animate().translationX(0F).setDuration(350).setStartDelay(100).start()

            layout_pemain.setOnClickListener {
                it.contentDescription = "Singleplayer"
                Intent(context, PemainActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }

        btn_cpu.setOnClickListener {
            imageMenu.setImageResource(R.drawable.slide2)
            tv_menu.text = username.plus(" VS CPU")

            //pass animation
            imageMenu.startAnimation(animImage)
            tv_menu.startAnimation(animTitle)

            //another animation
            btn_cpu.animate().scaleY(1F).scaleX(1F).setDuration(350).start()
            btn_pemain.animate().scaleY(0.7F).scaleX(0.7F).setDuration(350).start()
            dotmenu.animate().translationX(780F).setDuration(350).setStartDelay(100).start()

            layout_pemain.setOnClickListener {
                it.contentDescription = "Multiplayer"
                Intent(context, CpuActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }
}