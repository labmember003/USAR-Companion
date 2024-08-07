package com.falcon.usarcompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.falcon.usarcompanion.databinding.FragmentBranchesTabbedBinding
import com.falcon.usarcompanion.ui.main.SectionsPagerAdapter
import com.falcon.usarcompanion.ui.main.TAB_TITLES
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayoutMediator

class BranchesTabbedFragment : Fragment() {

    private lateinit var binding: FragmentBranchesTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentYear = arguments?.getInt("Year")
        //Toast.makeText(activity, "$currentYear", Toast.LENGTH_SHORT).show()
        binding = FragmentBranchesTabbedBinding.inflate(layoutInflater, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), this, currentYear) { subject ->
            val bundle = Bundle()
            bundle.putSerializable("CurrentSubject", subject)
            findNavController().navigate(R.id.action_BranchesTabbedFragment_to_contentActivity, bundle)
        }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = context?.getString(TAB_TITLES[position])
        }.attach()
        getActionBar()?.title = "Year " + currentYear.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as? MainActivity)?.supportActionBar
    }
}