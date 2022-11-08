package com.falcon.usarcompanion.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
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
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        //Toast.makeText(context, "hemlo", Toast.LENGTH_SHORT).show()
        return true
    }
}
/*
val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
val name = sharedPreferences.getString("name", "")
Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
 */