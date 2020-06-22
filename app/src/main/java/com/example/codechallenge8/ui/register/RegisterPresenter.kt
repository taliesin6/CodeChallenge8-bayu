package com.example.codechallenge8.ui.register

import android.util.Log
import com.example.codechallenge8.model.BodyRegister
import com.example.codechallenge8.model.RegisterResponse
import com.example.codechallenge8.api.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(
    private val apiServices: ApiServices
): InterfaceRegister.Presenter {

    var view: InterfaceRegister.View? = null

    companion object {
        val TAG = RegisterPresenter::class.java.simpleName
    }

    override fun register(bodyRegister: BodyRegister) {
        view?.showLoading()
        apiServices.register(bodyRegister).enqueue(object : Callback<RegisterResponse>{
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.onError(t.message.toString())
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.code() == 200 || response.code() == 201){
                    val model = response.body()
                    Log.d(TAG, model?.data.toString())
                    view?.onSuccess(model?.success.toString())
                } else {
                    view?.onError("Register Failed")
                }
                view?.hideLoading()
            }
        })
    }
}