package com.example.amiiboapi.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.example.amiiboapi.R
import com.example.amiiboapi.presentation.about_amiibo.AboutAmiiboFragment
import com.example.amiiboapi.presentation.amiibo_list.AmiiboListFragment
import com.example.amiiboapi.presentation.game_series_list.GameSeriesFragment
import com.example.amiiboapi.presentation.common.interfaces.Navigation
import com.example.amiiboapi.presentation.settings.SettingsFragment

/**
 * Главное Activity, являющееся хостом для всех фрагментов
 *
 * @author Murad Luguev on 08-08-2021
 */
class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restoreFragment()
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, true)
    }

    private fun restoreFragment() {
        val gameSeriesFragment = supportFragmentManager.findFragmentByTag(GameSeriesFragment.TAG)
        val amiiboListFragment = supportFragmentManager.findFragmentByTag(AmiiboListFragment.TAG)
        val aboutAmiiboFragment = supportFragmentManager.findFragmentByTag(AboutAmiiboFragment.TAG)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SettingsFragment.TAG)
        when {
            settingsFragment != null -> {
                replace(settingsFragment, SettingsFragment.TAG)
            }
            aboutAmiiboFragment != null -> {
                replace(aboutAmiiboFragment, AboutAmiiboFragment.TAG)
            }
            amiiboListFragment != null -> {
                replace(amiiboListFragment, AmiiboListFragment.TAG)
            }
            gameSeriesFragment != null -> {
                replace(gameSeriesFragment, GameSeriesFragment.TAG)
            }
            else -> showStartFragment()
        }
    }

    override fun move(destination: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, destination, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    private fun showStartFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, GameSeriesFragment.newInstance(), GameSeriesFragment.TAG)
            .commit()
    }

    private fun replace(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            move(SettingsFragment.newInstance(), SettingsFragment.TAG)
        }
        return true
    }

}