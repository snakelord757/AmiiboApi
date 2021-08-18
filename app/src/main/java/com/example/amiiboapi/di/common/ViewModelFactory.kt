package com.example.amiiboapi.di.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amiiboapi.di.amiibo.component.DaggerAmiiboComponent
import com.example.amiiboapi.presentation.about_amiibo.AboutAmiiboViewModel
import com.example.amiiboapi.presentation.amiibo_list.AmiiboListViewModel
import com.example.amiiboapi.presentation.application.AmiiboApiApp
import com.example.amiiboapi.presentation.game_series_list.GameSeriesViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameSeriesViewModel::class.java -> { provideGameSeriesVieModel() }
            AmiiboListViewModel::class.java -> { provideAmiiboListViewModel() }
            AboutAmiiboViewModel::class.java -> { provideAboutAmiiboViewModel() }
            else -> throw IllegalStateException()
        }
    }

    private fun <T> provideGameSeriesVieModel(): T {
        val amiiboComponent = DaggerAmiiboComponent.builder()
            .appComponent(AmiiboApiApp.appComponent)
            .build()
        val gameSeriesViewModel = GameSeriesViewModel(
            amiiboComponent.amiiboInteractor(),
            amiiboComponent.schedulersProvider(),
            amiiboComponent.errorMapper()
        )
        return gameSeriesViewModel as T
    }

    private fun <T> provideAmiiboListViewModel(): T {
        val amiiboComponent = DaggerAmiiboComponent.builder()
            .appComponent(AmiiboApiApp.appComponent)
            .build()
        val amiiboListViewModel = AmiiboListViewModel(
            amiiboComponent.amiiboInteractor(),
            amiiboComponent.schedulersProvider(),
            amiiboComponent.errorMapper()
        )
        return amiiboListViewModel as T
    }

    private fun <T> provideAboutAmiiboViewModel(): T {
        val amiiboComponent = DaggerAmiiboComponent.builder()
            .appComponent(AmiiboApiApp.appComponent)
            .build()
        val aboutAmiiboViewModel = AboutAmiiboViewModel(
            amiiboComponent.amiiboInteractor(),
            amiiboComponent.schedulersProvider(),
            amiiboComponent.errorMapper()
        )
        return aboutAmiiboViewModel as T
    }
}