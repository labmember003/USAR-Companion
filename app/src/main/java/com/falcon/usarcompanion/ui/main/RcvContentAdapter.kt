package com.falcon.usarcompanion.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Content
import com.google.android.material.imageview.ShapeableImageView


class RcvContentAdapter(val context: Context, private val contents: List<Content>,
    val sectionType : String) : RecyclerView.Adapter<RcvContentAdapter.RcvContentViewHolder>(){
    lateinit var iconURL: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_subjects_item_layout, parent, false)
        return RcvContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcvContentViewHolder, position: Int) {
        holder.contentName.text = contents[position].name
        iconURL = contents[position].sourceUrl
        //val res: Int = context.getResources().getIdentifier("baseline_menu_black_24dp", "drawable", "app/src/main/res/drawable/baseline_menu_black_24dp.xml")
        //holder.icon.setImageResource(R.drawable.baseline_menu_black_24dp)
        //holder.icon.setImageResource(R.drawable.book)
        // put notes, files, books icon hardcoded and use it like above code !!!
        if (sectionType == "Notes & Files") {
            //iconURL = contents[position].sourceUrl
            holder.icon.setImageResource(R.drawable.notes)
        } else if (sectionType == "papers") {
            //iconURL = contents[position].sourceUrl
            holder.icon.setImageResource(R.drawable.exam)
        } else if (sectionType == "books") {
            holder.icon.setImageResource(R.drawable.book)
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

