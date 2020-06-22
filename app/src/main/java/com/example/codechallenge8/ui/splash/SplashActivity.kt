package com.example.codechallenge8.ui.splash

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.landingPage.LandingPageActivity
import com.example.codechallenge8.ui.menu.MainActivity
import com.example.codechallenge8.util.SharedPrefManager
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), InterfaceAuth.View {

    private val presenter by inject<AuthPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.view = this

        val restID = resources.getIdentifier("bright", "raw", packageName)
        val mediaPlayer = MediaPlayer.create(this, restID)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            startActivity(Intent(this, LandingPageActivity::class.java))
            finish()
        }

        val animTitle = AnimationUtils.loadAnimation(this, R.anim.title)
        val animAssets = AnimationUtils.loadAnimation(this, R.anim.bkg)

        imageTitle.startAnimation(animTitle)
        imageBatu.startAnimation(animAssets)
        imageKertas.startAnimation(animAssets)
        imageGunting.startAnimation(animAssets)

        val sp = SharedPrefManager.getInstance(this).putUser
        val token = sp.token
        val tokens = "Bearer ".plus(token)
        presenter.auth(tokens)
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}