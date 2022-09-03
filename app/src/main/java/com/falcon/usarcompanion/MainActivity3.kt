package com.falcon.usarcompanion

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.BaseBundle
import android.os.Bundle
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
import com.falcon.usarcompanion.databinding.ActivityMain3Binding
import com.falcon.usarcompanion.network.Section
import com.falcon.usarcompanion.network.Subject
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest


class MainActivity3 : AppCompatActivity() {

private lateinit var binding: ActivityMain3Binding

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
            Toast.makeText(this, "Permission mil gyi bisi", Toast.LENGTH_SHORT).show()
        }

        // This function is called when the user accepts or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when the user is prompt for permission.



        //
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        val result = intent.extras?.getSerializable("CurrentSubject") as Subject
        Log.i("aapkimausi", result.subjectName)

        val bundleNotes = Bundle(); val bundlePaper = Bundle(); val bundleBooks = Bundle()
        //bundleNotes.putSerializable("Notes", result.sections[0]) // 0 for notes and files 1 for papers and 2 for books
        val bundleKeyList = listOf<String>("Notes", "papers", "books")
        val bundleList = listOf<Bundle>(bundleNotes, bundlePaper, bundleBooks)
        for (i in 0..2) {
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

        val navController = findNavController(R.id.nav_host_fragment_activity_main3).also {
            it.setGraph(R.navigation.mobile_navigation, bundleNotes)
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation1, R.id.navigation2, R.id.navigation3))
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
            }
            true
        }

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
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }


}




