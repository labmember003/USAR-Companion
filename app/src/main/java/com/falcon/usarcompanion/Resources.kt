package com.falcon.usarcompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.falcon.usarcompanion.databinding.FragmentFirstBinding
import com.falcon.usarcompanion.databinding.FragmentResourcesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Resources.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        getActionBar()!!.setTitle("USAR Companion")
    }

    private fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)!!.getSupportActionBar()
    }


}