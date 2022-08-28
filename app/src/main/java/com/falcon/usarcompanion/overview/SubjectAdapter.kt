package com.falcon.usarcompanion.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Subject

private const val TYPE_HEADLINE = 0
private const val TYPE_SUBJECT = 1

class HeadlineItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
}

class SubjectItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var subjectIcon = itemView.findViewById<ImageView>(R.id.subjectIcon)
    var subjectName = itemView.findViewById<TextView>(R.id.subjectName)
}

class SubjectAdapter(
    val context: Context,
    private val oddSemesterSubjects: List<Subject>,
    private val evenSemesterSubjects: List<Subject>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADLINE -> HeadlineItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_headline_item_layout, null))
            TYPE_SUBJECT -> SubjectItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_subjects_item_layout, null))
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADLINE
            isOddSemester(position) -> TYPE_SUBJECT
            (position == oddSemesterSubjects.size + 1) -> TYPE_HEADLINE
            else -> TYPE_SUBJECT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{

        }
        when(holder) {
            is HeadlineItemViewHolder -> {
                holder.tvTitle.text = if (position == 0) "Semester 1" else "Semester 2"
            }
            is SubjectItemViewHolder -> {
                val subject = if (isOddSemester(position)) {
                    oddSemesterSubjects[position - 1]
                } else {
                    evenSemesterSubjects[position - (oddSemesterSubjects.size + 2)]
                }
                holder.subjectName.text = subject.subjectName
            }
            else -> throw IllegalStateException("Unknown holder: $holder")
        }
    }

    private fun isOddSemester(position: Int): Boolean {
        return (position - 1) < oddSemesterSubjects.size
    }

    override fun getItemCount(): Int {
        return oddSemesterSubjects.size + evenSemesterSubjects.size + 2
    }
}

