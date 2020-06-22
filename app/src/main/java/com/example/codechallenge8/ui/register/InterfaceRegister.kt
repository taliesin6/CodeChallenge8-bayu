package com.example.codechallenge8.ui.register

import com.example.codechallenge8.model.BodyRegister

interface InterfaceRegister {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun onError(msg: String)
        fun onSuccess(msg: String)
    }

    interface Presenter {
        fun register(bodyRegister: BodyRegister)
    }
}