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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class BlankFragment : Fragment() {
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
*/
    private lateinit var binding: ActivityYearTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }
    //////





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYearTabbedBinding.inflate(layoutInflater)
        (activity as MainActivity?)!!.setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(
            requireContext(),
            (activity as MainActivity?)!!.supportFragmentManager,
            onSubjectClick = { subject ->
                val bundle = Bundle()
                bundle.putSerializable("CurrentSubject", subject)
                findNavController().navigate(R.id.action_blankFragment_to_mainActivity3, bundle)
            }
        )
//        val sectionsPagerAdapter = SectionsPagerAdapter(, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }
}