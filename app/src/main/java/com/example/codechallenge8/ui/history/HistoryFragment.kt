package com.example.codechallenge8.ui.history

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallenge8.model.DataHistory
import com.example.codechallenge8.R
import com.example.codechallenge8.adapter.HistoryAdapter
import com.example.codechallenge8.ui.game.InterfacePemain
import com.example.codechallenge8.util.SharedPrefManager
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment(), InterfacePemain.View {

    private val presenter by inject<HistoryPresenter>()
    private val historyAdapter = HistoryAdapter()

    private val dialog: AlertDialog by lazy {
        SpotsDialog.Builder().setContext(activity).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.view = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = activity?.let { SharedPrefManager.getInstance(it).putUser }
        val token = sp?.token
        val tokens = "Bearer ".plus(token)

        presenter.getBattle(tokens)

        rv_history_battle.setHasFixedSize(true)
        rv_history_battle.layoutManager = LinearLayoutManager(activity)
        rv_history_battle.adapter = historyAdapter
    }

    override fun showLoading() {
        rv_history_battle.visibility = View.GONE
        tv_no_data_history.visibility = View.VISIBLE
        if (!dialog.isShowing) dialog.show()
    }

    override fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
        rv_history_battle.visibility = View.VISIBLE
        tv_no_data_history.visibility = View.GONE
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showResult(result: String): String {
        TODO("Not yet implemented")
    }

    override fun <T> showData(data: T) {
        historyAdapter.setData(data as MutableList<DataHistory>)
    }


}