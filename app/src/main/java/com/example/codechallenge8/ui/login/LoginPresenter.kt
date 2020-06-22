package com.example.codechallenge8.ui.login

import android.util.Log
import com.example.codechallenge8.model.BodyLogin
import com.example.codechallenge8.model.LoginResponse
import com.example.codechallenge8.api.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(
    private val apiServices: ApiServices
) : InterfaceLogin.Presenter {

    var view: InterfaceLogin.View? = null

    companion object {
        val TAG = LoginPresenter::class.java.simpleName
    }

    override fun login(bodyLogin: BodyLogin) {
        view?.showLoading()
        apiServices.login(bodyLogin).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.onError(t.message.toString())
                view?.hideLoading()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200 || response.code() == 201){
                    val model = response.body()
                    view?.onSuccess(model!!)
                } else {
                    view?.onError("Login Failed")
                }
                view?.hideLoading()
            }
        })
    }
}