package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

data class TraceRecord (
    val id: Int,
    val date: String,
    val time: String,
    val code: String
    )

class TraceAdapter(private val data:ArrayList<TraceRecord>, context: Context):
    RecyclerView.Adapter<TraceAdapter.ViewHolder>() {
    val ctx = context
    class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tv_date = v.findViewById<TextView>(R.id.tv_date)
        val tv_time = v.findViewById<TextView>(R.id.tv_time)
        val tv_code = v.findViewById<TextView>(R.id.tv_code)
        val bt_del_rec = v.findViewById<Button>(R.id.bt_del_rec)
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

        holder.bt_del_rec.setOnClickListener {
            val db = DBmain(this.ctx)
            val success = db.readableDatabase.delete("record","id=${data[position].id}",null)
            if(success>-1) {
                Toast.makeText(this.ctx, "successfully delete", Toast.LENGTH_SHORT).show();
                data.removeAt(position)
//                notifyItemRemoved(position) //會當掉
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}