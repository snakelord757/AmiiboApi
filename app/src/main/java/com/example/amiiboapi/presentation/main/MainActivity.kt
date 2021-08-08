package com.example.amiiboapi.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.amiiboapi.R
import com.example.amiiboapi.presentation.aboutAmiibo.AboutAmiiboFragment
import com.example.amiiboapi.presentation.amiiboList.AmiiboListFragment
import com.example.amiiboapi.presentation.gameSeriesList.GameSeriesFragment
import com.example.amiiboapi.presentation.common.interfaces.Navigation

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
    }

    private fun restoreFragment() {
        val gameSeriesFragment = supportFragmentManager.findFragmentByTag(GameSeriesFragment.TAG)
        val amiiboListFragment = supportFragmentManager.findFragmentByTag(AmiiboListFragment.TAG)
        val aboutAmiiboFragment = supportFragmentManager.findFragmentByTag(AboutAmiiboFragment.TAG)
        when {
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

}