package com.example.codechallenge8.ui.history

import android.util.Log
import com.example.codechallenge8.model.BattleResponse
import com.example.codechallenge8.model.BodyBattle
import com.example.codechallenge8.model.HistoryBattleResponse
import com.example.codechallenge8.api.ApiServices
import com.example.codechallenge8.ui.game.InterfacePemain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryPresenter(
    private val apiServices: ApiServices
) : InterfacePemain.Presenter {

    var view: InterfacePemain.View? = null

    companion object {
        val TAG = HistoryPresenter::class.java.simpleName
    }

    override fun saveBattle(token: String, bodyBattle: BodyBattle) {
        view?.showLoading()
        apiServices.saveBattle(
            token,
            bodyBattle
        ).enqueue(object : Callback<BattleResponse>{
            override fun onFailure(call: Call<BattleResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.onError(t.message.toString())
            }

            override fun onResponse(
                call: Call<BattleResponse>,
                response: Response<BattleResponse>
            ) {
                if (response.isSuccessful){
                    val model = response.body()
                    Log.d(TAG, "POST"+model?.data.toString())

                    view?.onSuccess(model?.success.toString())
                } else {
                    view?.onError("Battle not saved")
                }
                view?.hideLoading()
            }

        })
    }

    override fun getBattle(token: String) {
        view?.showLoading()
        apiServices.getBattle(token).enqueue(object : Callback<HistoryBattleResponse>{
            override fun onFailure(call: Call<HistoryBattleResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                view?.onError(t.message.toString())
            }

            override fun onResponse(
                call: Call<HistoryBattleResponse>,
                response: Response<HistoryBattleResponse>
            ) {
                if (response.isSuccessful){
                    val model = response.body()
                    Log.d(TAG, "GET"+model?.data.toString())

                    if (model != null){
                        view?.showData(model.data)
                    } else {
                        Log.d(TAG, ""+model)
                    }

                    view?.onSuccess(model?.success.toString())
                } else {
                    view?.onError("Battle is empty")
                }
                view?.hideLoading()
            }
        })
    }
}