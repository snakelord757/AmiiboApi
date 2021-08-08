package com.example.amiiboapi.presentation.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.domain.model.Error
import io.reactivex.disposables.Disposable

/**
 * Основная реализация ViewModel, которая
 * содержит поведение для состояния загруки и отображения ошибки
 *
 * @author Murad Luguev on 08-08-2021
 */
abstract class AppViewModel : ViewModel() {

    private val errorMutableLiveData = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = errorMutableLiveData

    private val showProgressBarMutableLiveData = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = showProgressBarMutableLiveData

    protected var singleTaskDisposable: Disposable? = null

    /**
     * Метод, для обработки ошибки
     *
     * @param throwable исключение, вызванное во время запроса
     */
    protected fun showError(throwable: Throwable) {
        hideProgressBar()
        performError(throwable)
    }

    private fun performError(throwable: Throwable) {
        errorMutableLiveData.value = ErrorMapper.mapError(throwable)
    }

    private fun hideProgressBar() {
        showProgressBarMutableLiveData.value = false
    }

    /**
     * Метод, для отображения резудьтата
     *
     * @param action действие, которое необходимо выполнить
     */
    protected fun resultReceived(action: () -> Unit) {
        hideProgressBar()
        action.invoke()
    }

    override fun onCleared() {
        singleTaskDisposable?.dispose()
    }
}