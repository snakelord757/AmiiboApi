package com.example.amiiboapi.presentation.about_amiibo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.presentation.common.SchedulersProvider
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.di.FakeDependencyInjector
import io.reactivex.Single

/**
 * ViewModel для отображения информации о выбранном предмете
 *
 * @property amiiboInteractor интерактор для получения информации из репозитория
 *
 * @author Murad Luguev on 08-08-2021
 */
class AboutAmiiboViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor(),
    private val schedulersProvider: SchedulersProvider = FakeDependencyInjector.injectSchedulersProvider(),
    errorMapper: ErrorMapper = FakeDependencyInjector.injectErrorMapper()
) : AppViewModel(errorMapper) {

    private val amiiboMutableLiveData = MutableLiveData<AmiiboModel>()
    val amiibo: LiveData<AmiiboModel>
        get() = amiiboMutableLiveData


    fun getInfoAbout(amiiboTail: String, forceReload: Boolean) {
        if (amiiboMutableLiveData.value == null || forceReload)
            loadInfoAbout(amiiboTail)
    }
    /**
     * Метод, для загрузки информации о выбранном предмете
     *
     * @param amiiboTail "хвост" предмета
     */
    private fun loadInfoAbout(amiiboTail: String) {
        showProgress()
        singleTaskDisposable = Single.fromCallable {
            amiiboInteractor.getInfoAboutAmiibo(amiiboTail)
        }
            .observeOn(schedulersProvider.main())
            .subscribeOn(schedulersProvider.io())
            .subscribe({ container ->
                resultReceived { amiiboMutableLiveData.value = container.amiibo.first() }
            }, { throwable ->
                showError(throwable)
            })
    }
}