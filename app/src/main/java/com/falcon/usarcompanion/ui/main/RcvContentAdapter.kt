package com.falcon.usarcompanion.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Content
import com.google.android.material.imageview.ShapeableImageView


class RcvContentAdapter(val context: Context, private val contents: List<Content>,
                        private val sectionType : String
                        , private val onContentClick : (String, String) -> Unit
    ): RecyclerView.Adapter<RcvContentAdapter.RcvContentViewHolder>(){
    private var lastPosition : Int = -1
    private lateinit var iconURL: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_subjects_item_layout, parent, false)
        return RcvContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcvContentViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val fileURL: String = contents[position].sourceUrl
            val titleAndFileName: String = extractProperName(contents[position].sourceUrl)
            onContentClick(fileURL, titleAndFileName)
            // TODO Name mei spaces bhi honge ager dikkat aayi tho change krna pdega
        }
        holder.contentName.text = contents[position].name
        iconURL = contents[position].sourceUrl
        //val res: Int = context.getResources().getIdentifier("baseline_menu_black_24dp", "drawable", "app/src/main/res/drawable/baseline_menu_black_24dp.xml")
        //holder.icon.setImageResource(R.drawable.baseline_menu_black_24dp)
        //holder.icon.setImageResource(R.drawable.book)
        // put notes, files, books icon hardcoded and use it like above code !!!
        when (sectionType) {
            "Notes & Files" -> {
                //iconURL = contents[position].sourceUrl
                holder.icon.setImageResource(R.drawable.notes)
            }
            "papers" -> {
                //iconURL = contents[position].sourceUrl
                holder.icon.setImageResource(R.drawable.exam)
            }
            "books" -> {
                holder.icon.setImageResource(R.drawable.book)
            }
            "Playlists" -> {
                holder.icon.setImageResource(R.drawable.playlisticon)
            }
            "Syllabus" -> {
                holder.icon.setImageResource(R.drawable.syllabusicon)
            }
        }
        setAnimation(holder.itemView.rootView, position)
    }

    private fun extractProperName(sourceUrl: String): String {
        for (i in (sourceUrl.indices).reversed()) {
            if (sourceUrl[i] == '/') {
                return sourceUrl.subSequence(i + 1, (sourceUrl.length)).toString()
            }
        }
        return ""
    }

    override fun getItemCount(): Int {
        return contents.size
    }
    class RcvContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentName: TextView = itemView.findViewById(R.id.subjectName)
        val icon: ShapeableImageView = itemView.findViewById(R.id.subjectIcon)
    }
    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}

