package com.example.amiiboapi.presentation.game_series_list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.di.FakeDependencyInjector
import com.example.amiiboapi.presentation.amiibo_list.AmiiboListFragment
import com.example.amiiboapi.presentation.common.BaseFragment
import com.example.amiiboapi.presentation.game_series_list.adapter.GameSeriesAdapter

/**
 * Фрагмент для отображения игровых серий
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesFragment : BaseFragment<GameSeriesViewModel>(R.layout.fragment_with_list) {

    private lateinit var gameSeriesRecyclerView: RecyclerView

    override fun provideViewModel(): GameSeriesViewModel {
        return ViewModelProvider(this, provideViewModelFactory())[GameSeriesViewModel::class.java]
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val interactor = FakeDependencyInjector.injectAmiiboInteractor(
                    PreferenceManager.getDefaultSharedPreferences(requireContext()),
                    getSharedPreferences())
                return GameSeriesViewModel(interactor) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameSeriesRecyclerView = view.findViewById(R.id.listOfElements)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        gameSeriesRecyclerView.layoutManager = linearLayoutManager
        viewModel.gameSeries.observe(viewLifecycleOwner, ::showGameSeries)
        viewModel.getGameSeries(false)
    }

    private fun showGameSeries(gameSeries: List<GameSeriesModel>) {
        val gameSeriesAdapter = GameSeriesAdapter(gameSeries, ::showAmiiboByGameSeries)
        gameSeriesRecyclerView.adapter = gameSeriesAdapter
        gameSeriesRecyclerView.visibility = View.VISIBLE
    }

    private fun showAmiiboByGameSeries(gameSeriesKey: String) {
        moveTo(
            AmiiboListFragment.newInstance(gameSeriesKey),
            AmiiboListFragment.TAG
        )
    }

    override fun doOnRefresh() {
        gameSeriesRecyclerView.visibility = View.GONE
        viewModel.getGameSeries(true)
    }

    companion object {
        const val TAG = "GameSeriesFragment"

        /**
         * Фабричный метод для создания экзмепляра [GameSeriesFragment]
         *
         * @return экземпляр [GameSeriesFragment]
         */
        fun newInstance(): GameSeriesFragment {
            return GameSeriesFragment()
        }
    }
}