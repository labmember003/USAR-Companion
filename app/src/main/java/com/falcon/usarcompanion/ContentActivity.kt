package com.falcon.usarcompanion

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BaseBundle
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.usarcompanion.databinding.ActivityContentBinding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.network.Subject
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest


class ContentActivity : AppCompatActivity() {

private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        //ActivityCompat.requestPermissions(this,,1)
        // Function to check and request permission.

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "bro Permission tho de", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                12);
        } else {
            Toast.makeText(this, "Permission mil gyi ", Toast.LENGTH_SHORT).show()
            //startDownloading()
        }

        // This function is called when the user accepts or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when the user is prompt for permission.



        //
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        val result = intent.extras?.getSerializable("CurrentSubject") as Subject
        Log.i("aapkimausi", result.subjectName)

        val bundleNotes = Bundle(); val bundlePaper = Bundle()
        val bundleBooks = Bundle(); val bundlePlaylist = Bundle(); val bundleSyallabus = Bundle()
        //bundleNotes.putSerializable("Notes", result.sections[0]) // 0 for notes and files 1 for papers and 2 for books
        val bundleKeyList = listOf<String>("Notes", "papers", "books", "Playlists", "Syllabus")
        val bundleList = listOf<Bundle>(bundleNotes, bundlePaper, bundleBooks, bundlePlaylist, bundleSyallabus)
        for (i in 0..4) {
            bundleList[i].putSerializable(bundleKeyList[i], result.sections[i])
            // if array pass krni ho tho :-
            //bundleList[i].putSerializable(bundleKeyList[i],ArrayList(result.sections))
            //bundleList[i].putSerializable(bundleKeyList[i],result.sections.toTypedArray())
            // this works because ArrayList aur TypedArray internally implements serializable
        }
        //val b = Bundle()
        //b.putSerializable("ma", result.sections[0].contents[0])
        //b.putParcelableArrayList("Product", result.sections[0])

        //

        val navController = findNavController(R.id.nav_host_fragment_contentActivity).also {
            it.setGraph(R.navigation.mobile_navigation, bundleNotes)
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation1, R.id.navigation2, R.id.navigation3, R.id.navigation4, R.id.navigation5))
        setupActionBarWithNavController(navController, appBarConfiguration)
        // navView.setupWithNavController(navController)
        Log.i("aapkimausi", result.sections[0].title)
        /*
        navView.setOnNavigationItemSelectedListener { item ->
            /*
            if (item.itemId == R.id.navigation1) {
                navController.navigate(item.itemId, bundleNotes)
            }
             */
            navController.navigate(item.itemId, bundleNotes)
            true
        }
         */

        navView.setOnItemSelectedListener { item ->
            //when (item.itemId) { }
            if (item.itemId == R.id.navigation1) {
                navController.navigate(item.itemId, bundleNotes)
            } else if (item.itemId == R.id.navigation2) {
                navController.navigate(item.itemId, bundlePaper)
            } else if (item.itemId == R.id.navigation3) {
                navController.navigate(item.itemId, bundleBooks)
            } else if (item.itemId == R.id.navigation4) {
                navController.navigate(item.itemId, bundlePlaylist)
            } else if (item.itemId == R.id.navigation5) {
                navController.navigate(item.itemId, bundleSyallabus)
            }
            true
        }

    }

    fun startDownloading(fileURL: String, titleAndFileName: String) {
        val url : String = fileURL
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(titleAndFileName)
        request.setDescription("File is donwloading")
        //request.setMimeType("application/pdf")
        request.setMimeType(getMimeType(fileURL))
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, titleAndFileName)
        Toast.makeText(baseContext, "Download has begun, See Notifications", Toast.LENGTH_LONG).show()
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun getMimeType(fileURL: String): String {
        val type = getType(fileURL)
        return when(type) {
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "ppt" -> "application/vnd.ms-powerpoint"
            "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            "xls" -> "application/vnd.ms-excel"
            "jpeg" -> "image/jpeg"
            "jpg" -> "image/jpeg"
            "txt" -> "text/plain"
            "htm" -> "text/html"
            "html" -> "text/html"
            "mp4" -> "video/mp4"
            "mp3" -> "audio/mpeg"
            "zip" -> "application/zip"
            else -> {
                Toast.makeText(baseContext, "UNKNOWN FILE TYPE, PLEASE CONTACT DEVELOPER", Toast.LENGTH_LONG).show()
                ""
            }
        }
    }

    private fun getType(fileURL: String): String {
        for (i in (fileURL.indices).reversed()) {
            if (fileURL[i] == '.') {
                return fileURL.subSequence(i + 1, (fileURL.length)).toString()
            }
        }
        return ""
    }

    /*
    fun startDownloading(fileURL: String, titleAndFileName: String) {
        val url : String = "https://github.com/labmember003/usar_data/raw/master/YEAR_1/Sem1/CommunicationSkills/Exam/MinorExam.pdf"
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("gggg")
        request.setDescription("File is donwloading")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "mausi.pdf")

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
     */

    override fun onBackPressed() {
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }


}




