package com.falcon.usarcompanion.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.falcon.usarcompanion.databinding.FragmentSecondInBnvBinding
import com.falcon.usarcompanion.network.Section

class DashboardFragment : Fragment() {

private var _binding: FragmentSecondInBnvBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

    _binding = FragmentSecondInBnvBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textDashboard
    dashboardViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val papers : Section?
        if (arguments != null) {
            papers = arguments?.getSerializable("papers") as Section?
            Log.i("billipaper", papers?.title.toString())
        }
        Log.i("billi","data reached in frag")
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}