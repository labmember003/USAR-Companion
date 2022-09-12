package com.falcon.usarcompanion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.falcon.usarcompanion.databinding.FragmentBranchesTabbedBinding
import com.falcon.usarcompanion.ui.main.SectionsPagerAdapter
import com.falcon.usarcompanion.ui.main.TAB_TITLES
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BranchesTabbedFragment : Fragment() {

    private lateinit var binding: FragmentBranchesTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val currentYear = arguments?.getInt("Year")
        //val currentYear = savedInstanceState?.getInt("Year")
        Toast.makeText(activity, "${currentYear}", Toast.LENGTH_SHORT).show()
        binding = FragmentBranchesTabbedBinding.inflate(layoutInflater, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, currentYear) { subject ->
            val bundle = Bundle()
            bundle.putSerializable("CurrentSubject", subject)
            findNavController().navigate(R.id.action_BranchesTabbedFragment_to_contentActivity, bundle)
        }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = context?.getString(TAB_TITLES[position])
        }.attach()

        return binding.root
    }
}