package com.falcon.usarcompanion.ui.main.syllabus

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.ContentActivity
import com.falcon.usarcompanion.databinding.FragmentSyllabusBinding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.ui.main.RcvContentAdapter
import com.google.android.gms.ads.AdRequest
import java.io.File

class SyllabusFragment : Fragment() {
    private var _binding: FragmentSyllabusBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val syllabusViewModel =
            ViewModelProvider(this).get(SyllabusViewModel::class.java)

        _binding = FragmentSyllabusBinding.inflate(inflater, container, false)
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
        // TODO mausi

        var syllabus : Section? = null
        if (arguments != null) {
            syllabus = arguments?.getSerializable("Syllabus") as Section?
            Log.i("billisyllabus", syllabus?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (syllabus?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${syllabus.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), syllabus?.contents!!, syllabus.title, ::onContentClick, ::shareFile)
        binding.rvContents.adapter = adapter
        binding.rvContents.layoutManager = LinearLayoutManager(requireContext())
        //
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun shareFile(fileName: String) {
//        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)  // This stores in phone's downloads folder, but the thing is we as an third party app don't have full access to it, So
        val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName) // We Will store in our app's private folder where we have full access of the entire files
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val photoURI = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            intent.putExtra(Intent.EXTRA_STREAM, photoURI)
            startActivity(Intent.createChooser(intent, "Share File"))
        } else {
            Toast.makeText(requireContext(), "First Download the file", Toast.LENGTH_SHORT).show()
        }
    }
    private fun onContentClick(fileURL: String, titleAndFileName: String) {
//        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), titleAndFileName)
        val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), titleAndFileName)
        if (!file.exists()) {
            (activity as ContentActivity?)!!.startDownloading(fileURL, titleAndFileName)
            (activity as ContentActivity?)!!.openFile(file, fileURL)
        } else {
            (activity as ContentActivity?)!!.openFile(file, fileURL)
        }
    }
}