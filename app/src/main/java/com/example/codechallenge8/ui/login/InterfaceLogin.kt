package com.example.codechallenge8.ui.login

import com.example.codechallenge8.model.BodyLogin
import com.example.codechallenge8.model.LoginResponse

interface InterfaceLogin {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun onSuccess(data: LoginResponse)
        fun onError(msg: String)
    }

    interface Presenter {
        fun login(bodyLogin: BodyLogin)
    }
}