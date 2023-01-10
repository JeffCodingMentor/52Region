package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class TraceRecord (
    val date: String,
    val time: String,
    val code: String
    )

class TraceAdapter(private val data:ArrayList<TraceRecord>):
    RecyclerView.Adapter<TraceAdapter.ViewHolder>() {
    class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tv_date = v.findViewById<TextView>(R.id.tv_date)
        val tv_time = v.findViewById<TextView>(R.id.tv_time)
        val tv_code = v.findViewById<TextView>(R.id.tv_code)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trace, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_date.text = data[position].date
        holder.tv_time.text = data[position].time
        holder.tv_code.text = data[position].code
    }

    override fun getItemCount(): Int {
        return data.size
    }
}