package com.falcon.usarcompanion

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.falcon.usarcompanion.ui.main.SectionsPagerAdapter
import com.falcon.usarcompanion.databinding.ActivityYearTabbedBinding
import com.falcon.usarcompanion.network.Subject

class YearTabbedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYearTabbedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYearTabbedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fun onSub (subject: Subject) {

        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, ::onSub)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}