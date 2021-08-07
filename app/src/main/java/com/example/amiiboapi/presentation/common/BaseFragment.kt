package com.example.amiiboapi.presentation.common

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.amiiboapi.R
import com.example.amiiboapi.domain.model.Error
import com.example.amiiboapi.presentation.common.viewmodel.AppViewModel

abstract class BaseFragment<VM>(@LayoutRes layoutResId: Int) : Fragment(layoutResId)
    where VM : AppViewModel {

    protected val viewModel: VM by lazy { provideViewModel() }
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessage: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBar)
        errorMessage = view.findViewById(R.id.errorMessage)
        viewModel.showProgressBar.observe(viewLifecycleOwner, ::updateProgressbarVisibility)
        viewModel.error.observe(viewLifecycleOwner, ::showErrorMessage)
    }

    private fun updateProgressbarVisibility(isVisible: Boolean) {
        if (!isVisible)
            progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(error: Error) {
        errorMessage.visibility = View.VISIBLE
        errorMessage.text = getString(error.messageResId)
    }

    abstract fun provideViewModel(): VM
}