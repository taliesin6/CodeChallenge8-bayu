package com.example.codechallenge8.ui.battle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallenge8.R
import com.example.codechallenge8.adapter.BattleAdapter
import com.example.codechallenge8.db.History
import kotlinx.android.synthetic.main.activity_save_battle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SaveBattleActivity : AppCompatActivity(), BattleInterface.View {

    private val battleAdapter = BattleAdapter()
    private val presenter by inject<BattlePresenter>()

    companion object {
        val TAG = SaveBattleActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_battle)

        presenter.view = this

        setSupportActionBar(toolbar_save_battle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_save_battle.setNavigationOnClickListener {
            onBackPressed()
        }

        rv_save_battle.setHasFixedSize(true)
        rv_save_battle.layoutManager = LinearLayoutManager(this)
        rv_save_battle.adapter = battleAdapter

       GlobalScope.launch(Dispatchers.Main) {
           val battle = presenter.getAllData()
            Log.d(TAG, ""+battle)

           runOnUiThread {
               if (battle.isNotEmpty()){
                   rv_save_battle.visibility = View.VISIBLE
                   tv_nodata_battle.visibility = View.GONE
                   battleAdapter.setData(battle)
               } else {
                   rv_save_battle.visibility = View.GONE
                   tv_nodata_battle.visibility = View.VISIBLE
               }
           }
       }

        battleAdapter.setOnClickListener(object : BattleAdapter.HistoryInterface{
            override fun deleteItem(history: History, position: Int) {
                presenter.delete(history)
                presenter.getAllData()
            }
        })

    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showResult(result: String): String {
        TODO("Not yet implemented")
    }

    override fun showData(history: MutableList<History>) {
        battleAdapter.setData(history)
    }
}