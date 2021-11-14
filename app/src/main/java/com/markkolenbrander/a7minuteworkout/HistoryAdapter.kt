package com.markkolenbrander.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//TODO: Make "delete history" button

class HistoryAdapter(private val context: Context, private val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val llHistoryMainItem: LinearLayout = view.findViewById(R.id.ll_history_item_main)
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val tvPosition: TextView = view.findViewById(R.id.tvPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_history_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items[position] //.get(position) = old way

        holder.tvPosition.findViewById<TextView>(R.id.tvPosition).text = (position+1).toString()
        holder.tvItem.findViewById<TextView>(R.id.tvItem).text = date

        if(position % 2 == 0){
            holder.llHistoryMainItem.setBackgroundColor(
                    Color.parseColor("#F8E3DD")
            )
        }else {
            holder.llHistoryMainItem.setBackgroundColor(
                    Color.parseColor("#FFFFFF")
                    )
        }

    }

}