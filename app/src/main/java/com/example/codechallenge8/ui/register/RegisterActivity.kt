package com.example.codechallenge8.ui.register

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codechallenge8.model.BodyRegister
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.login.LoginActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity(), InterfaceRegister.View {

    private val dialog: AlertDialog by lazy {
        SpotsDialog.Builder().setContext(this).build()
    }

    private val presenter by inject<RegisterPresenter>()

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter.view = this

        val sp = getSharedPreferences("code", Context.MODE_PRIVATE)
        val esp = sp.edit()

        btn_signup.setOnClickListener {
            val email = et_email.text.toString()
            val username = et_username.text.toString()
            val password = et_password.text.toString()

            esp.putString("email", et_email.text.toString())
            esp.putString("username", et_username.text.toString())
            esp.putString("password", et_password.text.toString())

            when {
                email.isEmpty() -> {
                    et_email.error = "Email Required"
                    et_email.requestFocus()
                    return@setOnClickListener
                }
                username.isEmpty() -> {
                    et_username.error = "Username Required"
                    et_username.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    et_password.error = "Password Required"
                    et_password.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    presenter.register(
                        BodyRegister(
                            email = email,
                            username = username,
                            password = password
                        )
                    )
                }
            }
        }

        if (sp.getBoolean("isLogin", false)){
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else if (sp.getBoolean("isRegister", false)){
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    override fun showLoading() {
        if (!dialog.isShowing) dialog.show()
    }

    override fun onError(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }
}