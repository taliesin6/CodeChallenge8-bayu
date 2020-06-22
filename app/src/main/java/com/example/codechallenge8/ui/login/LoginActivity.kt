package com.example.codechallenge8.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codechallenge8.model.BodyLogin
import com.example.codechallenge8.model.LoginResponse
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.menu.MainActivity
import com.example.codechallenge8.ui.register.RegisterActivity
import com.example.codechallenge8.util.SharedPrefManager
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity(), InterfaceLogin.View {

    private val dialog: AlertDialog by lazy {
        SpotsDialog.Builder().setContext(this).build()
    }

    private val presenter by inject<LoginPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.view = this

        val sp = getSharedPreferences("code", Context.MODE_PRIVATE)
        val esp = sp.edit()

        btn_signin.setOnClickListener {
            val emailLogin = et_email_login.text.toString()
            val passwordLogin = et_password_login.text.toString()

            when {
                emailLogin.isEmpty() -> {
                    et_email_login.error = "Email Required"
                    et_email_login.requestFocus()
                    return@setOnClickListener
                }
                passwordLogin.isEmpty() -> {
                    et_password_login.error = "Password Required"
                    et_password_login.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    presenter.login(
                        BodyLogin(
                            email = emailLogin,
                            password = passwordLogin
                        )
                    )
                    esp.putBoolean("isLogin", true)
                    esp.apply()
                }
            }
        }

        btn_toSignup.setOnClickListener {
            esp.clear()
            esp.commit()
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    override fun showLoading() {
        if (!dialog.isShowing) dialog.show()
    }

    override fun onSuccess(data: LoginResponse) {
        Toast.makeText(this, data.success.toString(), Toast.LENGTH_SHORT).show()
        SharedPrefManager.getInstance(this).saveUser(data.data!!)
        Intent(this, MainActivity::class.java).apply {
            putExtra("token", data.data.token)
            startActivity(this)
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}