package com.falcon.usarcompanion.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.ContentActivity
import com.falcon.usarcompanion.databinding.FragmentBooksBinding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.ui.main.RcvContentAdapter
import com.google.android.gms.ads.AdRequest
import java.io.File

class BooksFragment : Fragment() {

private var _binding: FragmentBooksBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val notificationsViewModel =
            ViewModelProvider(this).get(BooksViewModel::class.java)

    _binding = FragmentBooksBinding.inflate(inflater, container, false)
    val root: View = binding.root
/*
    val textView: TextView = binding.textNotifications
    notificationsViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
 */
    return root
  }
    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books : Section?
        if (arguments != null) {
            books = arguments?.getSerializable("books") as Section?
            Log.i("billibooks", books?.title.toString())
        }
        Log.i("billi","data reached in frag")
    }
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var books : Section? = null
        if (arguments != null) {
            books = arguments?.getSerializable("books") as Section?
            Log.i("billibooks", books?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (books?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${books.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), books?.contents!!, books.title, ::onContentClick, ::shareFile)
        binding.rvContentsBooks.adapter = adapter
        binding.rvContentsBooks.layoutManager = LinearLayoutManager(requireContext())
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