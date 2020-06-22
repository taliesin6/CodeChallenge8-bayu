package com.example.codechallenge8.ui.game

import com.example.codechallenge8.model.BodyBattle

interface InterfacePemain {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun onSuccess(msg: String)
        fun onError(msg: String)
        fun showResult(result: String) : String
        fun<T> showData(data: T)
    }

    interface Presenter {
        fun saveBattle(token: String, bodyBattle: BodyBattle)
        fun getBattle(token: String)
    }

}