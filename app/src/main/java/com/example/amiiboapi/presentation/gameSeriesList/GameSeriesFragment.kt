package com.example.amiiboapi.presentation.gameSeriesList

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.presentation.amiiboList.AmiiboListFragment
import com.example.amiiboapi.presentation.common.BaseFragment
import com.example.amiiboapi.presentation.extensions.navigation
import com.example.amiiboapi.presentation.gameSeriesList.adapter.GameSeriesAdapter

/**
 * Фрагмент для отображения игровых серий
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesFragment : BaseFragment<GameSeriesViewModel>(R.layout.fragment_with_list) {

    private lateinit var gameSeriesRecyclerView: RecyclerView

    override fun provideViewModel(): GameSeriesViewModel {
        return ViewModelProvider(this)[GameSeriesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameSeriesRecyclerView = view.findViewById(R.id.listOfElements)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        gameSeriesRecyclerView.layoutManager = linearLayoutManager
        viewModel.gameSeries.observe(viewLifecycleOwner, ::showGameSeries)
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