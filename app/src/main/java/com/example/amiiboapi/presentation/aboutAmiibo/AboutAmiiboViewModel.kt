package com.example.amiiboapi.presentation.aboutAmiibo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.presentation.di.FakeDependencyInjector
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AboutAmiiboViewModel(
    private val amiiboInteractor: AmiiboInteractor = FakeDependencyInjector.injectAmiiboInteractor()
) : AppViewModel() {

    private val amiiboMutableLiveData = MutableLiveData<AmiiboModel>()
    val amiibo: LiveData<AmiiboModel>
        get() = amiiboMutableLiveData

    fun loadInfoAbout(amiiboTail: String) {
        singleTaskDisposable = Single.fromCallable {
            amiiboInteractor.getInfoAboutAmiibo(amiiboTail)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ container ->
                updateProgressBarVisibility()
                amiiboMutableLiveData.value = container.amiibo.first()
            }, ::doOnError)
    }
}