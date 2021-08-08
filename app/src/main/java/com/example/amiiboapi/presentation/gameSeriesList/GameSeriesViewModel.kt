package com.example.amiiboapi.presentation.gameSeriesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.presentation.di.FakeDependencyInjector
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel для отображения списка серий игр
 *
 * @property amiiboInteractor интерактор для получения инофрмации из репозитория
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor()
) : AppViewModel() {

    init {
        getGameSeries()
    }

    private val gameSeriesMutableLiveData = MutableLiveData<List<GameSeriesModel>>()
    val gameSeries: LiveData<List<GameSeriesModel>>
        get() = gameSeriesMutableLiveData

    private fun getGameSeries() {
        singleTaskDisposable = Single.fromCallable {
            amiiboInteractor.getGameSeries()
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ container ->
                resultReceived { gameSeriesMutableLiveData.value = container.amiibo }
            }, { throwable ->
                showError(throwable)
            })
    }
}