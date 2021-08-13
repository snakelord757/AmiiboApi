package com.example.amiiboapi.presentation.amiibo_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.presentation.common.SchedulersProvider
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.di.FakeDependencyInjector
import io.reactivex.Single

/**
 * ViewModel для получения списка предметов, по заданной серии игр
 *
 * @property amiiboInteractor интерактор, для получения информациии из репоитория
 *
 * @author Murad Luguev on 08-08-2021
 */
class AmiiboListViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor(),
    private val schedulersProvider: SchedulersProvider = FakeDependencyInjector.injectSchedulersProvider(),
    errorMapper: ErrorMapper = FakeDependencyInjector.injectErrorMapper()
) : AppViewModel(errorMapper) {

    private val amiiboListMutableLiveData = MutableLiveData<List<AmiiboModelMinimal>>()
    val amiiboListMinimal: LiveData<List<AmiiboModelMinimal>>
        get() = amiiboListMutableLiveData

    /**
     * Получает список предметов по зданной игровой серии
     *
     * @param gameSeriesKey ключ игровой серии
     */
    fun getAmiiboByGameSeries(gameSeriesKey: String, forceReload: Boolean) {
        if (amiiboListMutableLiveData.value.isNullOrEmpty() || forceReload)
            loadAmiibo(gameSeriesKey)
    }

    private fun loadAmiibo(gameSeriesKey: String) {
        showProgress()
        singleTaskDisposable = Single.fromCallable {
            amiiboInteractor.getAmiiboByGameSeries(gameSeriesKey)
        }
            .observeOn(schedulersProvider.main())
            .subscribeOn(schedulersProvider.io())
            .subscribe({ container ->
                resultReceived { amiiboListMutableLiveData.value = container.amiibo }
            }, { throwable ->
                showError(throwable)
            })
    }
}