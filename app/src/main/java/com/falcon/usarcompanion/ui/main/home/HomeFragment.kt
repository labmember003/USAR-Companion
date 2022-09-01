package com.falcon.usarcompanion.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.databinding.FragmentHomeBinding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.ui.main.RcvContentAdapter

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

      /*
    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
       */
    return root
  }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var notes : Section? = null
        if (arguments != null) {
            notes = arguments?.getSerializable("Notes") as Section?
            Log.i("billinotes", notes?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (notes?.contents != null) {}

        val adapter = RcvContentAdapter(requireContext(), notes?.contents!!, notes.title)
        binding.rvContents.adapter = adapter
        binding.rvContents.layoutManager = LinearLayoutManager(requireContext())
        //

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}