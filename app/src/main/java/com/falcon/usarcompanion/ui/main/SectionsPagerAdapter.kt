package com.falcon.usarcompanion.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Subject
import com.falcon.usarcompanion.subjects.SubjectsFragment

val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

class SectionsPagerAdapter(
    private val context: Context,
    fragment: Fragment,
    private val currentYear: Int?,
    private val onSubjectClick: (subject: Subject) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            else -> {
                val b = Bundle()
                if (currentYear != null) {
                    b.putInt("currentYear", currentYear)
                    //currentBranch
                    //TAB_TITLES[position]
                    //contextForViewModel?.resources?.getString(R.string.tab_text_1)
                    //contextForSubjectAdapterAndViewModel?.resources?.getString(TAB_TITLES[position])
                    b.putString("currentBranch", context.getString(TAB_TITLES[position]))
                    //b.putString("currentBranch", contextForSubjectAdapterAndViewModel?.resources?.getString(TAB_TITLES[position]))
                }
                val fragment = SubjectsFragment()
                fragment.arguments = b // Passing Current Year to SubjectsFragment
                fragment.setOnPerformNavigation(onSubjectClick)
                fragment
            }
        }
    }
}
