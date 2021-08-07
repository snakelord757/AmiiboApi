package com.example.amiiboapi.presentation.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.domain.model.Error
import io.reactivex.disposables.Disposable

abstract class AppViewModel : ViewModel() {

    private val errorMutableLiveData = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = errorMutableLiveData
    private val showProgressBarMutableLiveData = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = showProgressBarMutableLiveData

    protected var singleTaskDisposable: Disposable? = null

    override fun onCleared() {
        singleTaskDisposable?.dispose()
    }

    protected fun doOnError(throwable: Throwable) {
        updateProgressBarVisibility()
        performError(throwable)
    }

    private fun performError(throwable: Throwable) {
        errorMutableLiveData.value = ErrorMapper.performError(throwable)
    }

    protected fun updateProgressBarVisibility() {
        showProgressBarMutableLiveData.value = false
    }
}