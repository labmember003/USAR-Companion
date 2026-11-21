package com.falcon.usarcompanion

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.usarcompanion.databinding.ActivityContentBinding
import com.falcon.usarcompanion.network.Network
import com.falcon.usarcompanion.network.Subject
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


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
            //Toast.makeText(this, "Permission mil gyi ", Toast.LENGTH_SHORT).show()
            //startDownloading()
        }
        // This function is called when the user accepts or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when the user is prompt for permission.



        //
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Keep the toolbar below the status bar; BottomNavigationView handles the navigation bar itself.
        binding.root.padForSystemBars(top = true)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        val result = intent.extras?.getSerializable("CurrentSubject") as Subject
        Log.i("aapkimausi", result.subjectName)

        val bundleNotes = Bundle(); val bundlePaper = Bundle()
        val bundleBooks = Bundle(); val bundlePlaylist = Bundle(); val bundleSyallabus = Bundle()
        val bundleKeyList = listOf<String>("Notes", "papers", "books", "Playlists", "Syllabus")
        val bundleList = listOf<Bundle>(bundleNotes, bundlePaper, bundleBooks, bundlePlaylist, bundleSyallabus)
        for (i in 0..4) {
            bundleList[i].putSerializable(bundleKeyList[i], result.sections[i])
        }

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
//                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
    private var downloadJob: Job? = null

    /**
     * Downloads a file inside the app and opens it when done. This replaces the system
     * DownloadManager, which always used the carrier's DNS and so failed on mobile networks
     * that block GitHub raw files; Network adds DNS-over-HTTPS and a jsDelivr fallback.
     */
    fun startDownloading(fileURL: String, titleAndFileName: String) {
        if (fileURL.isEmpty()) return
        if (downloadJob?.isActive == true) {
            Toast.makeText(this, "Please wait, a file is already downloading", Toast.LENGTH_SHORT).show()
            return
        }
        val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
        val target = File(dir, titleAndFileName)
        target.parentFile?.mkdirs()

        val progressBar = LinearProgressIndicator(this).apply {
            isIndeterminate = true
            max = 100
        }
        val padding = (24 * resources.displayMetrics.density).toInt()
        val container = FrameLayout(this).apply {
            setPadding(padding, padding / 2, padding, 0)
            addView(progressBar)
        }
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Downloading")
            .setMessage(titleAndFileName)
            .setView(container)
            .setCancelable(false)
            .setNegativeButton("Cancel") { _, _ -> downloadJob?.cancel() }
            .show()

        downloadJob = lifecycleScope.launch {
            val job = coroutineContext[Job]
            try {
                withContext(Dispatchers.IO) {
                    Network.download(fileURL, target, isCancelled = { job?.isActive == false }) { percent ->
                        if (percent >= 0) runOnUiThread {
                            progressBar.isIndeterminate = false
                            progressBar.setProgressCompat(percent, true)
                        }
                    }
                }
                dialog.dismiss()
                openFile(target, fileURL)
            } catch (e: Exception) {
                dialog.dismiss()
                if (e is CancellationException || job?.isActive == false) return@launch
                Log.w("ContentActivity", "Download failed: $fileURL", e)
                Toast.makeText(
                    this@ContentActivity,
                    "Download failed. Check your internet connection and try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun openFile(file: File, fileURL: String) {
        if (file.exists()) {
            val myIntent = Intent(Intent.ACTION_VIEW)
            val fileProviderUri =
                FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
            myIntent.data = fileProviderUri
            myIntent.setDataAndType(fileProviderUri, getMimeType(fileURL))
            myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val j = Intent.createChooser(myIntent, "Choose an application to open with:")
            startActivity(j)
        } else {
            Toast.makeText(
                baseContext,
                "File not found",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
