package com.falcon.usarcompanion

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.usarcompanion.databinding.ActivityMain3Binding
import com.falcon.usarcompanion.network.Subject
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity3 : AppCompatActivity() {

private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        val result = intent.extras?.getSerializable("CurrentSubject") as Subject
        Log.i("aapkimausi", result.subjectName)

        val bundleNotes = Bundle(); val bundlePaper = Bundle(); val bundleBooks = Bundle()
        //bundleNotes.putSerializable("Notes", result.sections[0]) // 0 for notes and files 1 for papers and 2 for books
        //
        val bundleKeyList = listOf<String>("Notes", "papers", "books")
        val bundleList = listOf<Bundle>(bundleNotes, bundlePaper, bundleBooks)
        for (i in 0..2) {
            bundleList[i].putSerializable(bundleKeyList[i], result.sections[i])
        }
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

}


