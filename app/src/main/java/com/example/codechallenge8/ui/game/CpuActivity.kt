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
import kotlinx.android.synthetic.main.activity_cpu.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class CpuActivity : AppCompatActivity(), BattleInterface.View {

    private var view1: View? = null
    private var view2: View? = null
    private var state = true
    private var pilih1 = ""

    private var suit = listOf("Batu", "Gunting", "Kertas")
    private val battleAdapter = BattleAdapter()
    private val presenter by inject<BattlePresenter>()
    private val presenterHistory by inject<HistoryPresenter>()

    companion object {
        val TAG = CpuActivity::class.java.simpleName
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpu)
        presenter.view = this

        setSupportActionBar(toolbar_cpu_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_cpu_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        var random = ""
        iv_batu1_cpu.setOnClickListener {
            if (state){
                it.background = getDrawable(R.drawable.select)
                view1 = it
                pilih1 = iv_batu1_cpu.contentDescription.toString()
                random = suit.random()
                Log.d(TAG, random)

                checkPemenangCpu(pilih1, randomHasil(random)).toString()
                btn_favourite_cpu.visibility = View.VISIBLE
                state = false
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_kertas1_cpu.setOnClickListener {
            if (state){
                it.background = getDrawable(R.drawable.select)
                view1 = it
                pilih1 = iv_kertas1_cpu.contentDescription.toString()
                random = suit.random()
                Log.d(TAG, random)

                checkPemenangCpu(pilih1, randomHasil(random)).toString()
                btn_favourite_cpu.visibility = View.VISIBLE
                state = false
            } else {
                Toast.makeText(this, "Can't select more", Toast.LENGTH_SHORT).show()
            }
        }

        iv_gunting1_cpu.setOnClickListener {
            if (state){
                it.background = getDrawable(R.drawable.select)
                view1 = it
                pilih1 = iv_gunting1_cpu.contentDescription.toString()
                random = suit.random()
                Log.d(TAG, random)

                checkPemenangCpu(pilih1, randomHasil(random)).toString()
                btn_favourite_cpu.visibility = View.VISIBLE
                state = false
            }
        }

        btn_favourite_cpu.setOnClickListener {
            //date
            val calendar = Calendar.getInstance()
            val dateTime = calendar.time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = sdf.format(dateTime)

            val mode = "Singleplayer"
            val sp = SharedPrefManager.getInstance(this).putUser
            val id = sp.id

            val result = tv_hasil_cpu.text.toString()

            GlobalScope.launch {
                val history = History(
                    id = id,
                    mode = mode,
                    result = result,
                    date = date
                )
                runOnUiThread {
                    presenter.insert(history)
                }

            }
            btn_favourite_cpu.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_black_24dp))
        }

        btn_reset_cpu.setOnClickListener {
            pilih1 = ""
            resetHasil(view1, view2)
            tv_hasil_cpu.text = "VS"
            state = true
            btn_favourite_cpu.visibility = View.GONE
            btn_favourite_cpu.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border_black_24dp))
        }
    }

    private fun randomHasil(random: String): String {
        when (random) {
            "Batu" -> {
                iv_batu2_cpu.background = getDrawable(R.drawable.select)
                view2 = iv_batu2_cpu
            }
            "Kertas" -> {
                iv_kertas2_cpu.background = getDrawable(R.drawable.select)
                view2 = iv_kertas2_cpu
            }
            "Gunting" -> {
                iv_gunting2_cpu.background = getDrawable(R.drawable.select)
                view2 = iv_gunting2_cpu
            }
        }
        return random
    }

    private fun resetHasil(view1: View?, view2: View?){
        view1?.background = getDrawable(android.R.color.transparent)
        view2?.background = getDrawable(android.R.color.transparent)
    }

    private fun checkResultCpu(firstPlayer: String, secondPlayer: String){
        if (firstPlayer == suit[0] && secondPlayer == suit[1] ||
            firstPlayer == suit[1] && secondPlayer == suit[2] ||
            firstPlayer == suit[2] && secondPlayer == suit[0]){
            showResult(" Menang")
        } else if ( firstPlayer == suit[0] && secondPlayer == suit[0] ||
            firstPlayer == suit[1] && secondPlayer == suit[1] ||
            firstPlayer == suit[2] && secondPlayer == suit[2]){
            showResult(" Draw")
        } else {
            showResult(" Kalah")
        }
    }

    private fun checkPemenangCpu(firstPlayer: String, secondPlayer: String){
        checkResultCpu(firstPlayer, secondPlayer)
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
        tv_hasil_cpu.text = hasil

        val sp = SharedPrefManager.getInstance(this).putUser
        val token = sp.token
        val tokens = "Bearer ".plus(token)
        val mode = "Singleplayer"
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
            mode = mode,
            result = results)
        )
        return hasil
    }

    override fun showData(history: MutableList<History>) {
        battleAdapter.setData(history)
    }
}