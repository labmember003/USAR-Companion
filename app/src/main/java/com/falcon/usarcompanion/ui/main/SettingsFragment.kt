package com.falcon.usarcompanion.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.MainActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.falcon.usarcompanion.R.xml.root_preferences, rootKey)
        val preference = preferenceManager.findPreference<Preference>("libraries")
        preference?.setOnPreferenceClickListener {
            startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            true
        }
        val preferenceBugReport = preferenceManager.findPreference<Preference>("bug")
        preferenceBugReport?.setOnPreferenceClickListener {
            (activity as MainActivity).composeEmail("Bug Report For USAR Companion")
            true
        }
        val preferenceContact = preferenceManager.findPreference<Preference>("contact")
        preferenceContact?.setOnPreferenceClickListener {
            (activity as MainActivity).composeEmail("Regarding App USAR Companion")
            true
        }
        //mListPreference?.onPreferenceClickListener = this
    }

    override fun onCreateRecyclerView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        savedInstanceState: Bundle?
    ): RecyclerView {
        return super.onCreateRecyclerView(inflater, parent, savedInstanceState)
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        //Toast.makeText(context, "hemlo", Toast.LENGTH_SHORT).show()
        return true
    }
}