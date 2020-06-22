package com.example.codechallenge8.ui.profile

import android.util.Log
import com.example.codechallenge8.model.LoginResponse
import com.example.codechallenge8.model.UsersResponse
import com.example.codechallenge8.api.ApiServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(
    private val apiServices: ApiServices
) : InterfaceProfile.Presenter {

    var view: InterfaceProfile.View? = null

    companion object {
        val TAG = ProfilePresenter::class.java.simpleName
    }

    override fun upload(
        token: String,
        file: MultipartBody.Part,
        username: String,
        email: String) {
        view?.showLoading()
        apiServices.upload(
            token,
            file,
            username.toRequestBody(
                "multipart/form-data".toMediaTypeOrNull()),
            email.toRequestBody(
                "multipart/form-data".toMediaTypeOrNull())
        ).enqueue(object : Callback<UsersResponse>{
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.hideLoading()
                view?.onError(t.message.toString())
            }

            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                if (response.isSuccessful){
                    val msg = response.body() as UsersResponse
                    view?.onSuccessUpdateUser(msg)
                    view?.onSuccess("Upload berhasil")
                } else {
                    val model = Gson().fromJson<LoginResponse>(
                        response.errorBody()?.charStream(),
                        object : TypeToken<LoginResponse>(){}.type
                    )
                    view?.onError(model.success.toString())
                }
                view?.hideLoading()
            }
        })
    }
}