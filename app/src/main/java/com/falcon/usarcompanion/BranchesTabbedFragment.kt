package com.falcon.usarcompanion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.falcon.usarcompanion.databinding.ActivityYearTabbedBinding
import com.falcon.usarcompanion.ui.main.SectionsPagerAdapter
import com.falcon.usarcompanion.ui.main.TAB_TITLES
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BranchesTabbedFragment : Fragment() {

    private lateinit var binding: ActivityYearTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ActivityYearTabbedBinding.inflate(layoutInflater, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(this) { subject ->
            val bundle = Bundle()
            bundle.putSerializable("CurrentSubject", subject)
            findNavController().navigate(R.id.action_blankFragment_to_mainActivity3, bundle)
        }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = context?.getString(TAB_TITLES[position])
        }.attach()

        return binding.root
    }
}