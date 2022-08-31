package com.falcon.usarcompanion.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.falcon.usarcompanion.databinding.FragmentNotificationsBinding
import com.falcon.usarcompanion.network.Section

class NotificationsFragment : Fragment() {

private var _binding: FragmentNotificationsBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

    _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textNotifications
    notificationsViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books : Section?
        if (arguments != null) {
            books = arguments?.getSerializable("books") as Section?
            Log.i("billibooks", books?.title.toString())
        }
        Log.i("billi","data reached in frag")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}