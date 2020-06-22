package com.example.codechallenge8.ui.profile

import com.example.codechallenge8.model.UsersResponse
import okhttp3.MultipartBody

interface InterfaceProfile {
    interface View {
        fun onSuccessUpdateUser(msg: UsersResponse){}
        fun hideLoading()
        fun showLoading()
        fun onSuccess(msg: String)
        fun onError(msg: String)
    }

    interface Presenter {
        fun upload(token: String, file: MultipartBody.Part, username: String, email: String)
    }
}