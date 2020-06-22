package com.example.codechallenge8.ui.splash

interface InterfaceAuth {

    interface View {
        fun onSuccess(msg: String)
        fun onError(msg: String)
    }

    interface Presenter {
        fun auth(token: String)
    }
}