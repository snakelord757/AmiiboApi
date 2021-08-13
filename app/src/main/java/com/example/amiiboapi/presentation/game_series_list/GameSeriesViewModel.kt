package com.example.amiiboapi.presentation.game_series_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.presentation.common.SchedulersProvider
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.di.FakeDependencyInjector
import io.reactivex.Single

/**
 * ViewModel для отображения списка серий игр
 *
 * @property amiiboInteractor интерактор для получения инофрмации из репозитория
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor(),
    private val schedulersProvider: SchedulersProvider = FakeDependencyInjector.injectSchedulersProvider(),
    errorMapper: ErrorMapper = FakeDependencyInjector.injectErrorMapper()
) : AppViewModel(errorMapper) {

    private val gameSeriesMutableLiveData = MutableLiveData<List<GameSeriesModel>>()
    val gameSeries: LiveData<List<GameSeriesModel>>
        get() = gameSeriesMutableLiveData

    fun getGameSeries(forceReload: Boolean) {
        if (gameSeriesMutableLiveData.value.isNullOrEmpty() || forceReload)
            loadGameSeries()
    }

    private fun loadGameSeries() {
        showProgress()
        singleTaskDisposable = Single.fromCallable {
            amiiboInteractor.getGameSeries()
        }
            .observeOn(schedulersProvider.main())
            .subscribeOn(schedulersProvider.io())
            .subscribe({ container ->
                resultReceived { gameSeriesMutableLiveData.value = container.amiibo }
            }, { throwable ->
                showError(throwable)
            })
    }
}