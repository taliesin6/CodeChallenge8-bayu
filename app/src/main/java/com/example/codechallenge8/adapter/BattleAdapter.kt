package com.example.codechallenge8.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge8.R
import com.example.codechallenge8.db.History
import kotlinx.android.synthetic.main.item_battle.view.*

class BattleAdapter : RecyclerView.Adapter<BattleAdapter.ViewHolder>() {

    private lateinit var listener: HistoryInterface
    private val items: MutableList<History> = mutableListOf()

    fun setOnClickListener(listener: HistoryInterface){
        this.listener = listener
    }

    fun setData(items: MutableList<History>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(history: History, position: Int){
            itemView.tv_date_battle.text = history.date
            itemView.tv_mode_battle.text = history.mode
            itemView.tv_result_battle.text = history.result
            listener.let {
                itemView.btn_delete_battle.setOnClickListener {
                    listener.deleteItem(history, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_battle, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    interface HistoryInterface {
        fun deleteItem(history: History, position: Int)
    }
}