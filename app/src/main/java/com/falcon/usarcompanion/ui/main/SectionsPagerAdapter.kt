package com.falcon.usarcompanion.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.network.Subject
import com.falcon.usarcompanion.overview.OverviewFragment

val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

class SectionsPagerAdapter(
    fragment: Fragment,
    private val onSubjectClick: (subject: Subject) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            else -> {
                val fragment = OverviewFragment()
                fragment.setOnPerformNavigation(onSubjectClick)
                fragment
            }
        }
    }
}