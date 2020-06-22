package com.example.codechallenge8.ui.splash

import android.util.Log
import com.example.codechallenge8.api.ApiServices
import com.example.codechallenge8.model.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthPresenter(private val apiServices: ApiServices) : InterfaceAuth.Presenter {

    companion object {
        val TAG = AuthPresenter::class.java.simpleName
    }

    var view: InterfaceAuth.View? = null

    override fun auth(token: String) {
        apiServices.auth(token).enqueue(object : Callback<AuthResponse>{
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.onError(t.message.toString())
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.code() == 200 || response.code() == 201){
                    val model = response.body()
                    view?.onSuccess(model?.success.toString())

                } else {
                    view?.onError("Auth Failed")
                }
            }

        })
    }
}