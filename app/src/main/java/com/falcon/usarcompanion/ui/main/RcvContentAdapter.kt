package com.falcon.usarcompanion.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Content
import com.google.android.material.imageview.ShapeableImageView

class RcvContentAdapter(val context: Context, private val contents: List<Content>,
    val sectionType : String) : RecyclerView.Adapter<RcvContentAdapter.RcvContentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_subjects_item_layout, parent, false)
        return RcvContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcvContentViewHolder, position: Int) {
        holder.contentName.text = contents[position].name
        if (sectionType == "Notes & Files") {
            //holder.icon
            //set it using glide / coil
        } else if (sectionType == "papers") {

        } else if (sectionType == "books") {

        }
    }

    override fun getItemCount(): Int {
        return contents.size
    }
    class RcvContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentName = itemView.findViewById<TextView>(R.id.subjectName)
        val icon = itemView.findViewById<ShapeableImageView>(R.id.subjectIcon)
    }
}

