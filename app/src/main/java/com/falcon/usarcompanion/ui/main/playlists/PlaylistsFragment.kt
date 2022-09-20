package com.falcon.usarcompanion.ui.main.playlists

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.ContentActivity
import com.falcon.usarcompanion.databinding.FragmentPlaylistsBinding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.ui.home.NotesViewModel
import com.falcon.usarcompanion.ui.main.RcvContentAdapter

class PlaylistsFragment : Fragment() {
    private var _binding: FragmentPlaylistsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PlaylistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val playlistsViewModel =
            ViewModelProvider(this).get(PlaylistsViewModel::class.java)
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO mausi

        var playlists : Section? = null
        if (arguments != null) {
            playlists = arguments?.getSerializable("Playlists") as Section?
            Log.i("billiplaylists", playlists?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (playlists?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${playlists.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), playlists?.contents!!, playlists.title, ::onContentClick)
        binding.rvContents.adapter = adapter
        binding.rvContents.layoutManager = LinearLayoutManager(requireContext())
        //

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /*
        val callback2 = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_homeFragment_to_mainActivity3)
        }
     */
    fun onContentClick(fileURL: String, titleAndFileName: String) {
        (activity as ContentActivity?)!!.startDownloading(fileURL, titleAndFileName)
    }

}