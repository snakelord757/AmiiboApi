package com.example.amiiboapi.presentation.amiiboList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.presentation.di.FakeDependencyInjector
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AmiiboListViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor()
) : AppViewModel() {

    private val amiiboListMutableLiveData = MutableLiveData<List<AmiiboModelMinimal>>()
    val amiiboListMinimal: LiveData<List<AmiiboModelMinimal>>
        get() = amiiboListMutableLiveData

    fun getAmiiboByGameSeries(gameSeriesKey: String) {
        if (amiiboListMutableLiveData.value == null) {
            singleTaskDisposable = Single.fromCallable {
                amiiboInteractor.getAmiiboByGameSeries(gameSeriesKey)
            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {container ->
                        updateProgressBarVisibility()
                        amiiboListMutableLiveData.value = container.amiibo }
                    , ::doOnError)
        }
    }
}