package com.example.amiiboapi.presentation.amiibo_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.presentation.about_amiibo.AboutAmiiboFragment
import com.example.amiiboapi.presentation.amiibo_list.adapter.AmiiboAdapter
import com.example.amiiboapi.presentation.application.AmiiboApiApp
import com.example.amiiboapi.presentation.common.BaseFragment

/**
 * Фрагмент для отображения предметов по выбранной игровой серии
 *
 * @author Murad Luguev on 08-08-2021
 */
class AmiiboListFragment : BaseFragment<AmiiboListViewModel>(R.layout.fragment_with_list) {

    private lateinit var amiiboListRecyclerView: RecyclerView

    override fun getViewModelClass(): Class<AmiiboListViewModel> {
        return AmiiboListViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amiiboListRecyclerView = view.findViewById(R.id.listOfElements)
        viewModel.amiiboListMinimal.observe(viewLifecycleOwner, ::showList)
        val gridLayoutManager = GridLayoutManager(requireContext(), ITEMS_SPAN_COUNT)
        amiiboListRecyclerView.layoutManager = gridLayoutManager
        loadAmiiboList()
    }

    private fun showList(amiiboList: List<AmiiboModelMinimal>) {
        val amiiboAdapter = AmiiboAdapter(getShowPicsParameter(), amiiboList, ::showAmiiboInfo)
        amiiboListRecyclerView.adapter = amiiboAdapter
        amiiboListRecyclerView.visibility = View.VISIBLE
    }

    private fun showAmiiboInfo(amiiboTail: String) {
        moveTo(
            AboutAmiiboFragment.newInstance(amiiboTail),
            AboutAmiiboFragment.TAG
        )
    }

    private fun getShowPicsParameter(): Boolean {
        val settingsPref = AmiiboApiApp.appComponent.settingsSharedPreferences()
        return settingsPref.getBoolean(getString(R.string.show_pics_key), true)
    }

    override fun doOnRefresh() {
        amiiboListRecyclerView.visibility = View.GONE
        loadAmiiboList(true)
    }

    private fun loadAmiiboList(forceReload: Boolean = false) {
        val args = requireArguments()
        if (args.containsKey(GAME_SERIES_KEY))
            viewModel.getAmiiboByGameSeries(args.getString(GAME_SERIES_KEY)!!, forceReload)
    }

    companion object {
        const val TAG = "AmiiboListFragment"
        private const val GAME_SERIES_KEY = "game_series_key"
        private const val ITEMS_SPAN_COUNT = 2

        /**
         * Фабричный метод для создания экземпяра [AmiiboListFragment] с заданным [GAME_SERIES_KEY]
         *
         * @param gameSeriesKey ключ игровой серии
         * @return экземпляр [AmiiboListFragment]
         */
        fun newInstance(gameSeriesKey: String): AmiiboListFragment {
            val args = Bundle()
            args.putString(GAME_SERIES_KEY, gameSeriesKey)
            val amiiboListFragment = AmiiboListFragment()
            amiiboListFragment.arguments = args
            return amiiboListFragment
        }
    }
}