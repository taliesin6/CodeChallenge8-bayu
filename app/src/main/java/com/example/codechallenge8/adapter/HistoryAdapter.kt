package com.example.codechallenge8.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge8.model.DataHistory
import com.example.codechallenge8.R
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val list: MutableList<DataHistory> = mutableListOf()): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setData(list: MutableList<DataHistory>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun String.parseDate(): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK)
            val date = inputFormat.parse(this)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("ID"))
            return outputFormat.format(date)
        }

        fun bind(history: DataHistory){
            itemView.tv_date_history.text = history.createdAt?.parseDate()
            itemView.tv_mode_history.text = history.mode
            itemView.tv_result_history.text = history.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}