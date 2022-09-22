package com.falcon.usarcompanion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.falcon.usarcompanion.databinding.ActivityMainBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }

        binding.navView.setNavigationItemSelectedListener {
             if (it.itemId == R.id.getDirections) {
                 val intent = Intent(
                     Intent.ACTION_VIEW,
                     Uri.parse("http://maps.google.com/maps?34.34&daddr=28.66488568388205,77.30043327394083")
                 )
                 binding.drawerLayout.close()
                 startActivity(intent)
                 return@setNavigationItemSelectedListener true
            }
            else if (it.itemId == R.id.share) {
                 val sendIntent: Intent = Intent().apply {
                     action = Intent.ACTION_SEND
                     putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                     type = "text/plain"
                 }
                 val shareIntent = Intent.createChooser(sendIntent, null)
                 startActivity(shareIntent)
                 binding.drawerLayout.close()
                 return@setNavigationItemSelectedListener true
             }
            val handled = NavigationUI.onNavDestinationSelected(it, navController)
            if (handled) {
                binding.drawerLayout.close()
            }
            handled
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(binding.drawerLayout)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }
}