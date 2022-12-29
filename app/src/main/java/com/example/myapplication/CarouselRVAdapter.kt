package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext

class CarouselRVAdapter(private val carouselDataList: ArrayList<String>):
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    private lateinit var context: Context
    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        context = parent.context
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val imageViewMark = holder.itemView.findViewById<ImageView>(R.id.imageViewMark)
        val textViewFinish = holder.itemView.findViewById<TextView>(R.id.textViewFinish)
        val textViewDozeNo = holder.itemView.findViewById<TextView>(R.id.textViewDozeNo)
        val textViewFinishHL = holder.itemView.findViewById<TextView>(R.id.textViewFinishHL)
        if (carouselDataList[position]=="yes") {
            textViewFinish.setText(R.string.Finish)
            textViewFinishHL.setText(R.string.FinishHL)
            textViewFinishHL.setBackgroundResource(R.color.green)
            imageViewMark.setImageResource(R.drawable.a2_14)
        }
        else {
            textViewFinish.setText(R.string.NotFinish)
            textViewFinishHL.setText(R.string.NotFinishHL)
            textViewFinish.setTextColor(ContextCompat.getColor(context, R.color.red))
            textViewDozeNo.setTextColor(ContextCompat.getColor(context, R.color.red))
            textViewFinishHL.setBackgroundResource(R.color.red)
            imageViewMark.setImageResource(R.drawable.a2_15)
        }
        when (position) {
            0->
            {
                textViewDozeNo.text = "第一劑疫苗"
            }
            1->
            {
                textViewDozeNo.text = "第二劑疫苗"
            }
            2->
            {
                textViewDozeNo.text = "第三劑疫苗"
            }
        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}