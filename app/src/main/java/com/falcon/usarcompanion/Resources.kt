package com.falcon.usarcompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.falcon.usarcompanion.databinding.FragmentFirstBinding
import com.falcon.usarcompanion.databinding.FragmentResourcesBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.NonDisposableHandle.parent

class Resources : Fragment() {

    private var _binding: FragmentResourcesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_resources, container, false)
        _binding = FragmentResourcesBinding.inflate(inflater, container, false)

// TODO: Add adView to your view hierarchy.




        var currentYear = 0
        listOf(binding.year1, binding.year2, binding.year3, binding.year4).forEach{
            currentYear++
            val bundle = Bundle()
            bundle.putInt("Year", currentYear)
            it.setOnClickListener {
                findNavController().navigate(R.id.action_resources_to_BranchesTabbedFragment, bundle)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(activity as MainActivity).actionBar?.setTitle("mausi")
        //getActionBar()!!.setTitle("USAR Companion")

        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        /*
        // making resource fragment as start destination
        1. NavGraph mei start destination ko first fragment se resource fragment kr diya
        2. resources fragment mei onViewCreated mei getActionBar waala chla rrhe the usko comment kr diya but aciton bar name ki problem fix nhi hui
        3. project level search kiya "fragment_resources", nav graph mei ek label milla usko change kr diya jo chahiye usmei..
        replaced it with "@string/app_name"
        */

    }

    private fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)!!.getSupportActionBar()
    }


}