package com.example.codechallenge8.ui.battle

import com.example.codechallenge8.db.History

interface BattleInterface {

    interface View {
        fun onSuccess(msg: String)
        fun onError(msg: String)
        fun showResult(result: String) : String
        fun showData(history: MutableList<History>)
    }

    interface Presenter {
        fun insert(history: History)
        fun getAllData() : MutableList<History>
        fun delete(history: History)
    }
}