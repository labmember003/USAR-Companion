package com.falcon.usarcompanion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.falcon.usarcompanion.network.Merge

class MyAdapter(private val merges: List<Merge>): Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.merchName.text = merges[position].merchName
        holder.merchPrice.text = merges[position].merchPrice
    }

    override fun getItemCount(): Int {
        return merges.size
    }
    class MyViewHolder(itemView: View): ViewHolder(itemView) {
        var merchName: TextView = itemView.findViewById(R.id.merchName)
        var merchPrice: TextView = itemView.findViewById(R.id.merchPrice)
    }


}

