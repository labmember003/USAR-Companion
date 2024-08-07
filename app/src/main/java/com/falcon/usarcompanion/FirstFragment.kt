package com.falcon.usarcompanion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.falcon.usarcompanion.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //findNavController().navigate(R.id.action_FirstFragment_to_campusMap)
            //findNavController().navigate(R.id.action_FirstFragment_to_aboutFragment)
            //findNavController().navigate(R.id.action_FirstFragment_to_yearTabbedActivity)
            //findNavController().navigate(R.id.action_FirstFragment_to_overviewFragment)
            //findNavController().navigate(R.id.action_FirstFragment_to_BranchesTabbedFragment)
            findNavController().navigate(R.id.action_FirstFragment_to_resources)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
/*
// CODE TO LAUNCH USAR IN GOOGLE MAPS
val intent = Intent(
    Intent.ACTION_VIEW,
    Uri.parse("http://maps.google.com/maps?34.34&daddr=28.66488568388205, 77.30043327394083")
)
startActivity(intent)
 */