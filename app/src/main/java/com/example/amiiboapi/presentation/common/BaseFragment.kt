package com.example.amiiboapi.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.amiiboapi.R
import com.example.amiiboapi.domain.model.Error
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel
import com.example.amiiboapi.presentation.extensions.navigation

/**
 * Основная реализация фрагмента, который содержит
 * базовое поведение для отобрадения состояния загрузки и отображения ошибки
 *
 * @param VM тип ViewModel, которую содержит фрагмент. Наследуется от [AppViewModel]
 *
 * @param layoutResId идентификатор верстки, которую отображает фрагмент
 *
 * @author Murad Luguev on 08-08-2021
 */
abstract class BaseFragment<VM>(@LayoutRes layoutResId: Int) : Fragment(layoutResId)
        where VM : AppViewModel {

    protected val viewModel: VM by lazy { provideViewModel() }
    private lateinit var factory: ViewModelProvider.Factory
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var errorMessage: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        viewModel.showProgressBar.observe(viewLifecycleOwner, ::updateProgressbarVisibility)
        viewModel.error.observe(viewLifecycleOwner, ::showErrorMessage)
    }

    private fun initUI(view: View) {
        errorMessage = view.findViewById(R.id.errorMessage)
        swipeToRefreshLayout = view.findViewById(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener(getRefreshListener())
        swipeToRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.teal_200))
    }

    private fun updateProgressbarVisibility(isVisible: Boolean) {
        swipeToRefreshLayout.isRefreshing = isVisible
    }

    private fun showErrorMessage(error: Error) {
        errorMessage.visibility = View.VISIBLE
        errorMessage.text = getString(error.messageResId)
    }

    private fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return SwipeRefreshLayout.OnRefreshListener {
            errorMessage.visibility = View.GONE
            doOnRefresh()
        }
    }

    /**
     * Метод для навигации между фрагментами
     *
     * @param destination фрагмент, на который надо переместиться
     * @param tag тэг фрагмента, на который надо переместиться
     */
    protected fun moveTo(destination: Fragment, tag: String) {
        navigation.move(
            destination,
            tag
        )
    }

    protected fun getSharedPreferences(): SharedPreferences {
        return requireContext().getSharedPreferences(AMIIBO_PREFERENCES, Context.MODE_PRIVATE)
    }

    /**
     * Метод для предоставления ViewModel
     *
     * @return ViewModel заданного типа
     */
    abstract fun provideViewModel(): VM

    /**
     * Метод, сожержащий действие, которое необходимо выполнить в [SwipeRefreshLayout.OnRefreshListener]
     */
    abstract fun doOnRefresh()

    abstract fun provideViewModelFactory(): ViewModelProvider.Factory

    companion object {
        private const val AMIIBO_PREFERENCES = "amiibo_preferences"
    }
}