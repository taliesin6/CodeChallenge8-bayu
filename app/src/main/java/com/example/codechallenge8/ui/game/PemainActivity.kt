package com.example.codechallenge8.ui.game

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.codechallenge8.model.BodyBattle
import com.example.codechallenge8.R
import com.example.codechallenge8.adapter.BattleAdapter
import com.example.codechallenge8.db.History
import com.example.codechallenge8.ui.history.HistoryPresenter
import com.example.codechallenge8.ui.battle.BattleInterface
import com.example.codechallenge8.ui.battle.BattlePresenter
import com.example.codechallenge8.util.SharedPrefManager
import kotlinx.android.synthetic.main.activity_pemain.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class PemainActivity : AppCompatActivity(), BattleInterface.View {

    private var pilih1 = ""
    private var pilih2 = ""
    private var state1 = true
    private var state2 = true
    private var view1: View? = null
    private var view2: View? = null
    private var suit = listOf("Batu", "Gunting", "Kertas")

    private val presenter by inject<BattlePresenter>()
    private val presenterHistory by inject<HistoryPresenter>()
    private val battleAdapter = BattleAdapter()

    companion object {
        val TAG = PemainActivity::class.java.simpleName
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemain)

        presenter.view = this

        setSupportActionBar(toolbar_pemain_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_pemain_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        iv_batu1.setOnClickListener {
            if (state1){
                it.background = getDrawable(R.drawable.select)
                pilih1 = iv_batu1.contentDescription.toString()
                view1 = it
                state1 = false
                Log.d(TAG, pilih1)
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_batu2.setOnClickListener {
            if (state2){
                it.background = getDrawable(R.drawable.select)
                pilih2 = iv_batu2.contentDescription.toString()
                view2 = it
                state2 = false
                Log.d(TAG, pilih2)

                checkPemenang(pilih1, pilih2)
                btn_favourite.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_kertas1.setOnClickListener {
            if (state1){
                it.background = getDrawable(R.drawable.select)
                pilih1 = iv_kertas1.contentDescription.toString()
                view1 = it
                state1 = false
                Log.d(TAG, pilih1)
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_kertas2.setOnClickListener {
            if (state2){
                it.background = getDrawable(R.drawable.select)
                pilih2 = iv_kertas2.contentDescription.toString()
                view2 = it
                state2 = false
                Log.d(TAG, pilih2)

                checkPemenang(pilih1, pilih2)
                btn_favourite.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_gunting1.setOnClickListener {
            if (state1){
                it.background = getDrawable(R.drawable.select)
                pilih1 = iv_gunting1.contentDescription.toString()
                view1 = it
                state1 = false
                Log.d(TAG, pilih1)
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_gunting2.setOnClickListener {
            if (state2){
                it.background = getDrawable(R.drawable.select)
                pilih2 = iv_gunting2.contentDescription.toString()
                view2 = it
                state2 = false
                Log.d(TAG, pilih2)

                checkPemenang(pilih1, pilih2)
                btn_favourite.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        btn_favourite.setOnClickListener {
            //date
            val calendar = Calendar.getInstance()
            val dateTime = calendar.time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = sdf.format(dateTime)

            val mode = "Multiplayer"
            val sp = SharedPrefManager.getInstance(this).putUser
            val id = sp.id

            val result = tv_hasil.text.toString()

            GlobalScope.launch {
                val history = History(
                    id = id,
                    mode = mode,
                    result = result,
                    date = date
                )
                Log.d(TAG, history.toString())
                presenter.insert(history)
            }
            btn_favourite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_black_24dp))
        }

        btn_reset.setOnClickListener {
            pilih1 = ""
            pilih2 = ""
            resetHasil(view1, view2)
            tv_hasil.text = "VS"
            state1 = true
            state2 = true
            btn_favourite.visibility = View.GONE
            btn_favourite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border_black_24dp))
        }
    }

    private fun resetHasil(view1: View?, view2: View?){
        view1?.background = getDrawable(android.R.color.transparent)
        view2?.background = getDrawable(android.R.color.transparent)
    }

    private fun checkResult(firstPlayer: String, secondPlayer: String){
        if (firstPlayer == suit[0] && secondPlayer == suit[1] ||
            firstPlayer == suit[1] && secondPlayer == suit[2] ||
            firstPlayer == suit[2] && secondPlayer == suit[0]
        ) {
            showResult(" Menang")
        } else if ( firstPlayer == suit[0] && secondPlayer == suit[0] ||
                    firstPlayer == suit[1] && secondPlayer == suit[1] ||
                    firstPlayer == suit[2] && secondPlayer == suit[2]
        ){
            showResult(" Draw")
        } else {
            showResult(" Kalah")
        }
    }

    private fun checkPemenang(firstPlayer: String, secondPlayer: String){
        checkResult(firstPlayer, secondPlayer)
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showResult(result: String) : String {
        val user = SharedPrefManager.getInstance(this).user
        val username = user.username
        val hasil = username.plus(result)
        tv_hasil.text = hasil

        val sp = SharedPrefManager.getInstance(this).putUser
        val token = sp.token
        val tokens = "Bearer ".plus(token)
        val mode = "Multiplayer"
        var results = ""

        when (result) {
            " Menang" -> {
                results = "Player Win"
            }
            " Draw" -> {
                results = "Draw"
            }
            " Kalah" -> {
                results = "Opponent Win"
            }
        }

        presenterHistory.saveBattle(tokens, BodyBattle(
            mode = mode, result = results
        )
        )

        return hasil
    }

    override fun showData(history: MutableList<History>) {
        battleAdapter.setData(history)
    }
}