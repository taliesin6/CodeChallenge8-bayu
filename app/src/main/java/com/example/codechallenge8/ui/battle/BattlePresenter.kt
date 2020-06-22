package com.example.codechallenge8.ui.battle

import com.example.codechallenge8.db.History
import com.example.codechallenge8.db.HistoryDB

class BattlePresenter(private val db: HistoryDB) :
    BattleInterface.Presenter {

    var view: BattleInterface.View? = null

    override fun insert(history: History) {
        db.historyDao().insert(history)
        val data = db.historyDao().getAllHistory()
        view?.showData(data)
    }

    override fun getAllData() : MutableList<History>{
        return db.historyDao().getAllHistory()

    }

    override fun delete(history: History) {
        db.historyDao().delete(history)
        val data = db.historyDao().getAllHistory()
        view?.showData(data)
    }
}