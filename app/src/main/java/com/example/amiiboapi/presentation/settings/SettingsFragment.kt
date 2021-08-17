package com.example.amiiboapi.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.amiiboapi.R

/**
 * Фрагмент настроек
 *
 * @author Murad Luguev on 17-08-2021
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    companion object {
        const val TAG = "SettingsFragment"

        /**
         * Фабричный метод для создания экземпляра [SettingsFragment]
         *
         * @return экземпрял [SettingsFragment]
         */
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}